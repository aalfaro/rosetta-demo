package com.rosetta.demo.common.cq.utils;

import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.auth.Authenticator;
import org.apache.sling.api.auth.NoAuthenticationHandlerException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.ELEvaluator;

public final class PageUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PageUtils.class);

	/** Constant for the page property redirectTarget. */
	public static final String STR_REDIRECT_TARGET = "redirectTarget";

	/** Path to site mappings in JCR (with trailing slash) */
	public static final String PATH_SITE_MAPPINGS = "/etc/map/http/";

	public static final String CQ_PROPERTY_INTERNAL_REDIRECT = "sling:internalRedirect";

	public static final int RESPONSE_CODE_404 = 404;

	// Name of the cq:Page should be displayed in case of 404
	public static final String PAGE_404_NAME = "404";
	public static final String PAGE_404_EXTENSION = ".html";

	/** JCR content node */
	public static final String JCR_CONTENT_NODE = "jcr:content";

	private static final String MESSAGE_ERROR_MISSING_AUTHENTICATION_SERVICE = 
			"Cannot login: Missing Authenticator service";
	private static final String MESSAGE_ERROR_NO_AUTHENTICATION_HANDLER = 
			"Cannot login: No Authentication Handler is willing to authenticate";
	private static final String MESSAGE_404_ERROR_RECURSING_UP_FROM_PREFIX = 
			"Cannot find 404 error page recursing up from ";

	public static final String REQUEST_ATTRIBUTE_SLING_BINDINGS = "org.apache.sling.api.scripting.SlingBindings";

	/**
	 * Constructor method. Private access
	 */
	private PageUtils() {
	}

	public static boolean hasPageProperty(Page page, String propertyName) {
		return hasNodeProperty(page.adaptTo(Node.class), propertyName);
	}

	public static boolean hasNodeProperty(Node node, String propertyName) {
		try {
			return node.hasProperty(propertyName);
		} catch (RepositoryException e) {
			LOG.error(e.getMessage(), e);
		}
		return false;
	}

	public static String getPageProperty(Page page, String propertyName) {
		return getNodeProperty(page.adaptTo(Node.class), propertyName);
	}

	public static String getNodeProperty(Node originalPageNode, String propertyName) {
		if (originalPageNode != null) {
			try {
				Node contentNode = originalPageNode.getNode(PageUtils.JCR_CONTENT_NODE);
				Property property = contentNode.getProperty(propertyName);
				if (property.getValue() != null) {
					return property.getValue().getString();
				}
			} catch (PathNotFoundException e) {
				LOG.debug(e.getMessage(), e);
			} catch (RepositoryException e) {
				LOG.debug(e.getMessage(), e);
			}
		}

		return null;
	}

	public static String[] getNodePropertyArray(Node originalPageNode, String propertyName) {
		if (originalPageNode != null) {
			try {
				Node contentNode = originalPageNode.getNode(PageUtils.JCR_CONTENT_NODE);
				Property property = contentNode.getProperty(propertyName);
				Value[] values = property.getValues();

				if (values != null && values.length > 0) {
					String[] valuesArray = new String[values.length];

					for (int i = 0; i < values.length; i++) {
						valuesArray[i] = values[i].getString();
					}

					return valuesArray;
				}

			} catch (PathNotFoundException e) {
				LOG.debug(e.getMessage(), e);
			} catch (RepositoryException e) {
				LOG.debug(e.getMessage(), e);
			}
		}

		return null;
	}

	public static Locale getResourceLanguage(Resource resource) {
		if (resource == null) {
			return Locale.ENGLISH;
		}
		return getPageLanguage(resource.adaptTo(Page.class));
	}

	public static Locale getPageLanguage(Page page) {
		if (page == null) {
			return Locale.ENGLISH;
		}
		Locale language = null;

		// Go through the parents to try and find a language for the page
		while (language == null && page != null) {
			language = page.getLanguage(false);
			page = page.getParent();
		}

		return language;
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName) && cookie.getMaxAge() != 0) {
					return cookie;
				}
			}
		}

		return null;
	}

	public static String getResolvedPath(String path, String defaultPath, SlingHttpServletRequest slingRequest,
			PageContext pageContext) {
		String location = path;
		if (StringUtils.isNotBlank(location)) {
			// resolve variables in path
			location = ELEvaluator.evaluate(location, slingRequest, pageContext);
			// only map URLs that are not fully qualified
			if (!location.startsWith("http")) {
				location = slingRequest.getResourceResolver().map(slingRequest, location) + ".html";
			}
		} else {
			location = defaultPath;
		}
		return location;
	}

	/**
	 * Checks whether the user is authenticated or not. Comes from the original Sling 404 error handler servlet.
	 * 
	 * @param request
	 *            .
	 * @return true if user is not authenticated.
	 */
	public static boolean isAnonymousUser(HttpServletRequest request) {
		return request.getAuthType() == null || request.getRemoteUser() == null
				|| "anonymous".equals(request.getRemoteUser());
	}

	/**
	 * Checks whether the request comes from a browser. Comes from the original Sling 404 error handler servlet.
	 * 
	 * @param request
	 *            .
	 * @return true if user is not authenticated.
	 */
	public static boolean isBrowserRequest(HttpServletRequest request) {
		// check if user agent contains "Mozilla" or "Opera"
		final String userAgent = request.getHeader("User-Agent");
		return userAgent != null && (userAgent.indexOf("Mozilla") > -1 || userAgent.indexOf("Opera") > -1);
	}

	public static void redirectToLoginIfNeeded(HttpServletRequest request, HttpServletResponse response) {
		// this.slingRepository = slingRepository;
		// decide whether to redirect to the (wcm) login page, or to send a plain 404
		if (PageUtils.isAnonymousUser(request) && PageUtils.isBrowserRequest(request)) {

			SlingBindings slingBindings = (SlingBindings) request.getAttribute(REQUEST_ATTRIBUTE_SLING_BINDINGS);
			Authenticator auth = slingBindings.getSling().getService(Authenticator.class);
			if (auth != null) {
				try {
					auth.login(request, response);

					// login has been requested, nothing more to do
					return;
				} catch (NoAuthenticationHandlerException nahe) {
					slingBindings.getLog().warn(MESSAGE_ERROR_NO_AUTHENTICATION_HANDLER);
				}
			} else {
				slingBindings.getLog().warn(MESSAGE_ERROR_MISSING_AUTHENTICATION_SERVICE);
			}

		}
	}

	public static Page getBaseSitePageForRequest(SlingHttpServletRequest request) {
		Page ret = null;
		// Constructing path to the node possibly contains mapping to the site base node.
		// Should be /etc/map/http/[domain].[port]
		String mapNodeName = request.getServerName() + "." + request.getServerPort();
		String mapNodePath = PATH_SITE_MAPPINGS + mapNodeName;
		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			// Getting node contains mapping for the requested site.
			Resource res = resourceResolver.getResource(mapNodePath);
			if (res != null) {
				Node node = res.adaptTo(Node.class);

				// Reading base site path from sling:internalRedirect property.
				String baseSitePath = "";
				if (node.getProperty(CQ_PROPERTY_INTERNAL_REDIRECT).isMultiple()) {
					baseSitePath = node.getProperty(CQ_PROPERTY_INTERNAL_REDIRECT).getValues()[0].getString();
				} else {
					baseSitePath = node.getProperty(CQ_PROPERTY_INTERNAL_REDIRECT).getString();
				}

				ret = resourceResolver.getResource(baseSitePath).adaptTo(Page.class);
			} else {
				LOG.warn("No mapping node found at " + mapNodePath);
			}
		} catch (Exception e) {
			LOG.error("An error occured during getting base site page for request:" + e.getMessage(), e);
		}
		return ret;
	}

	// ============== 404 handling helper methods ==============

	/**
	 * Gets path to 404 error page.
	 * 
	 * @param siteBasePath
	 *            JCR path to the root node of a given site. Usually this is set as sling:internalRedirect property on
	 *            the /etc/map/http/[domain].[port] node.
	 * @return JCR path to the 404 page. Can be used as an URI for redirects and forwards. Null if not found.
	 */
	public static String get404PagePathBySiteBasePath(Page baseSitePage) {
		String path = null;
		try {
			// searching a cq page with name '404'
			Node currentNode = baseSitePage.adaptTo(Node.class);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Searching 404 page at: " + currentNode.getPath());
			}
			while (!currentNode.getPath().equals("/")) {
				if (currentNode.hasNode(PAGE_404_NAME)) {
					Node tempNode = currentNode.getNode(PAGE_404_NAME);
					if (tempNode.isNodeType(NameConstants.NT_PAGE)) {
						path = tempNode.getPath();
						break;
					}
				}
				// Recursing up to find 404 page.
				if (LOG.isDebugEnabled()) {
					LOG.debug("404 page not found at: " + currentNode.getPath() + " Moving up one level.");
				}
				currentNode = currentNode.getParent();
			}
		} catch (Exception e) {
			String baseSitePath = "";
			if (baseSitePage != null) {
				baseSitePath = baseSitePage.getPath();
			}
			LOG.warn(MESSAGE_404_ERROR_RECURSING_UP_FROM_PREFIX + ": " + baseSitePath + ". " + e.getMessage(), e);
		}

		// Simplify URL if the path to the 404 page contains the mapping of the current host.
		// It's useful when using redirects instead of forwards because it makes the redirect url nicer.
		// if (path.indexOf(siteBasePath) == 0) {
		// path = path.replaceFirst(siteBasePath, "");
		// }

		return path;
	}

	public static void handle404ErrorForRequest(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		if (!response.isCommitted()) {
			// Checks if the user is logged in. If not then forwards to the login page before serving the request.
			PageUtils.redirectToLoginIfNeeded(request, response);

			// since we are serving a 404, let's set the status to 404
			response.setStatus(RESPONSE_CODE_404);

			// Finding location of the 404 page
			String errorPagePath = PageUtils.get404PagePathBySiteBasePath(PageUtils.getBaseSitePageForRequest(request));

			// If it exists displaying it.
			if (errorPagePath != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("404 page for request '" + request.getRequestURL() + " is '" + errorPagePath + "'");
				}

				RequestDispatcher requestDispatcher = request.getRequestDispatcher(errorPagePath + PAGE_404_EXTENSION);
				try {
					requestDispatcher.forward(request, response);
				} catch (Exception e) {
					LOG.error("An error occured during forwarding to 404 error page", e);
				}
			} else {
				LOG.warn("No 404 page found for request: " + request.getRequestURL());
			}
		} else {
			LOG.warn("Cannot perform 404 processing on a response that has already been committed");
		}
	}

}
