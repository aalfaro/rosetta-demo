package com.rosetta.demo.bl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for export JCR contents
 * 
 * @author Arturo Alfaro
 */
@SlingServlet(metatype = false, generateComponent = true, methods = "GET",
        paths = "/system/rosetta/servlets/contentexporter")
public class ContentExporterServlet extends SlingAllMethodsServlet {

	public static final String SERVLET_PATH = "/system/rosetta/servlets/contentexporter";

	private static final long serialVersionUID = 6945138940293153570L;

	private final Logger log = LoggerFactory.getLogger(ContentExporterServlet.class);

	/**
	 * Method doGet for the servlet processing.
	 * 
	 * @param request SlingHttpServletRequest object with the request
	 * @param response SlingHttpServletResponse object with the response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
	        IOException {
		writeResponse(request, response);
	}
	
	/**
	 * Method doPost for the servlet processing.
	 * 
	 * @param request SlingHttpServletRequest object with the request
	 * @param response SlingHttpServletResponse object with the response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
	        IOException {
		writeResponse(request, response);
	}

	/**
	 * Method to write the CQ5 response.
	 * 
	 * @param request SlingHttpServletRequest object with the request
	 * @param response SlingHttpServletResponse object with the response
	 * @throws IOException
	 */
	private void writeResponse(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print("This is a CQ5 Response ... ");
		pw.print(getPageContent(request, "/content/helloworld"));
		pw.flush();
		pw.close();
	}

	/**
	 * Method to get the content of a page path
	 * 
	 * @param SlingHttpServletRequest request
	 * @param String pagePath the path of the requested page
	 * @return String containing the HTML content representation of the page
	 */
	private String getPageContent(SlingHttpServletRequest request, String pagePath) {

		final int DEFAULT_PORT = 80;
		final String JCR_CONTENT_LOCATION = "/_jcr_content/content.html";
		final int TIMEOUT = 5000;
		final String HTTP_404_ERROR_RESPONSE = "404";

		ResourceResolver resourceResolver = request.getResource().getResourceResolver();
		Resource resource = resourceResolver.getResource(pagePath);

		if (resource != null) {

			String content = null;

			// Create a HttpClient object
			
			HttpClient client = new HttpClient();
			
			// Establish a connection within 5 seconds
			client.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);

			String url;
			if (request.getRemotePort() == DEFAULT_PORT) {
				url = "http://" + request.getServerName() + pagePath + JCR_CONTENT_LOCATION;
			} else {
				url = "http://" + request.getServerName() + ":" + request.getServerPort() + pagePath
				        + JCR_CONTENT_LOCATION;
			}

			// If user is working on author instance, there is gonna be a 404 code because of the credentials
			HttpMethod method = new GetMethod(url);

			try {
				client.executeMethod(method);
				if (method.getStatusCode() == HttpStatus.SC_OK
				        && !StringUtils.containsIgnoreCase(method.getResponseBodyAsString(), "http 404")) {
					content = method.getResponseBodyAsString();
					content = content + "<!-- Path of the content : " + pagePath + " -->";
				} else {
					log.info("Error 404 - Resource is not available");
					content = HTTP_404_ERROR_RESPONSE;
				}
			} catch (HttpException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}

			// Clean up the connection resources
			method.releaseConnection();

			// Return the content
			return content;
		} else {
			log.info("Error 404 - Resource is not available");
			return HTTP_404_ERROR_RESPONSE;
		}
	}
}