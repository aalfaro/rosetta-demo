package com.rosetta.demo.bl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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
		writeResponse(response);
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
		writeResponse(response);
	}

	/**
	 * Method to write the CQ5 response.
	 * 
	 * @param response SlingHttpServletResponse object with the response
	 * @throws IOException
	 */
	private void writeResponse(SlingHttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print("This is a CQ5 Response ... ");
		pw.flush();
		pw.close();
	}
}