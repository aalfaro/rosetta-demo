<%--

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%><%@include file="/libs/foundation/global.jsp" %><%
%><%@ page import="com.day.cq.commons.Doctype,
                   org.apache.commons.lang.StringEscapeUtils" %><%
    String xs = Doctype.isXHTML(request) ? "/" : "";
    String favIcon = currentDesign.getPath() + "/favicon.ico";
    //if we had a fevicon or desing here it world be referenced
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }
%><head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"<%=xs%>>
    <meta http-equiv="keywords" content="<%= StringEscapeUtils.escapeHtml(WCMUtils.getKeywords(currentPage, false)) %>"<%=xs%>>
    <meta http-equiv="description" content="<%= StringEscapeUtils.escapeHtml(properties.get("jcr:description", "")) %>"<%=xs%>>
    <cq:include script="headlibs.jsp"/>
    <cq:includeClientLib categories="cq.foundation-main"/>
    <!-- includes our css -->
    <%currentDesign.writeCssIncludes(pageContext); %>
    <!-- sidekick -->
    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <% if (favIcon != null) { %>
    <link rel="icon" type="image/vnd.microsoft.icon" href="<%= favIcon %>"<%=xs%>>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="<%= favIcon %>"<%=xs%>>
    <% } %>
    <title><%= currentPage.getTitle() == null ? currentPage.getName() : currentPage.getTitle() %> | Training</title>
</head>