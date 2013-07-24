<%--

  (Training) Basic Content Page Component component.

  (Training) Basic Content Page Component + properties

--%>
<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false" %>
<html>
    <head>
        <title>CQ5 Basic Content | <%= currentPage.getTitle() %></title>
        <cq:include script="/libs/wcm/core/components/init/init.jsp" />
    </head>
    <body>
        <!--
        -----------------------------------------------
        using the default objects loaded
        by the <cq:defineObjects/> tag on global.jsp 
        -----------------------------------------------
        -->
        <!-- currentPage is the current CQ WCM page object, 
        type com.day.cq.wcm.api.Page interface -->
        <h1><%= currentPage.getTitle() %></h1>
        <hr/>          
        <h3>Page builtin properties</h3>
        <p>Page Title (getPageTitle): <%= currentPage.getPageTitle() %></p>
        <p>Page Name (getName): <%= currentPage.getName() %></p>
        <p>Page Description (getDescription): <%= currentPage.getDescription() %></p>
        <p>Page Path(getPath)<%= currentPage.getPath() %></p>
        <p>Page Depth(getDepth)<%= currentPage.getDepth() %></p>
        <br/>
        <h3>Page properties (properties object)</h3>
        <p>Page Title ("jcr:title"): <%= properties.get("jcr:title",String.class) %></p>
        <p>Page Title ("pageTitle"): <%= properties.get("pageTitle",String.class) %></p>
        <p>Page Title ("title"): <%= properties.get("title",String.class) %></p>
        <p>Page Name ("name"): <%= properties.get("name",String.class) %></p>
        <p>Page Description ("jcr:description"): <%= properties.get("jcr:description",String.class) %></p>
        <p>Page Description ("description"): <%= properties.get("description",String.class) %></p>
        <p>Page Path("path"): <%= properties.get("path",String.class) %></p>
        <p>Page Depth("depth"): <%= properties.get("depth",String.class) %></p>   
        <hr/>
        <!-- currentNode is the current JCR node object, remember
        a page is a node type cq:Page, the objec type is
        javax.jcr.Node interface -->
        <h3>Node built-in properties</h3> 
        <p>Node Name (getName): <%= currentNode.getName() %></p>
        <p>Node Path (getPath): <%= currentNode.getPath() %></p>
        <p>Node Depth (getDepth): <%= currentNode.getDepth() %></p>
        <br/>
        <h3>Node properties</h3>
        <p>Node title ("jcr:title"): <%= currentNode.getProperty("jcr:title").getString() %></p>
        <%--  Node title ("title"): <%= currentNode.getProperty("title").getString() %> --%>
        <hr/>
        <!-- currentStyle is the current style object of the current cell,
        design properties are inherit by child nodes, the objec type is
        com.day.cq.wcm.api.designer.Style interface -->
        <h3>Style builtin properties</h3>
        <p>Style path (getPath): <%= currentDesign.getCssPath() %></p>
        <p>Style ID (getId): <%= currentDesign.getId() %></p>
    </body>
</html>