<%--

  (Training) Basic Content Page Component component.

  (Training) Basic Content Page Component + properties

--%>
<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false" %>
<html>
    <head>
        <title>CQ5 Basic Content | <%= currentPage.getTitle() %> (Plain)</title>
        <cq:include script="/libs/wcm/core/components/init/init.jsp" /> 
    </head>
    <body class="print">
        <!--
        -----------------------------------------------
        using the default pbjects loaded
        by the <cq:defineObjects/> tag on global.jsp 
        -----------------------------------------------
        -->
        <!-- currentPage is the current CQ WCM page object, 
        type com.day.cq.wcm.api.Page interface -->
        <h1><%= currentPage.getTitle() %> | (Plain)</h1>
        <hr/>
        <table>
            <tr>
                <td colspan=2><h3>Page builtin properties</h3></th>
            </tr>
            <tr>
                <td>Properties</td><td>Value</td>
            </tr>
            <tr><td>Page Name (getPageTitle)</td><td><%= currentPage.getPageTitle() %></td></tr>
            <tr><td>Page Name (getName)</td><td><%= currentPage.getName() %></td></tr>
            <tr><td>Page Description (getDescription)</td><td><%= currentPage.getDescription() %></td></tr>
            <tr><td>Page Path(getPath)</td><td><%= currentPage.getPath() %></td></tr>
            <tr><td>Page Depth(getDepth)</td><td><%= currentPage.getDepth() %></td></tr>
        </table>
        <br/>       
        <table>
            <tr>
               <td colspan=2><h3>Page properties (properties object)</h3></th>
            </tr>
            <tr><td>Page Name ("jcr:title")</td><td><%= properties.get("jcr:title",String.class) %></td></tr>
            <tr><td>Page Name ("pageTitle")</td><td><%= properties.get("pageTitle",String.class) %></td></tr>
            <tr><td>Page Name ("title")</td><td><%= properties.get("title",String.class) %></td></tr>
            <tr><td>Page Name ("name")</td><td><%= properties.get("name",String.class) %></td></tr>   
            <tr><td>Page Description ("jcr:description")</td><td><%= properties.get("jcr:description",String.class) %></td></tr>
            <tr><td>Page Description ("description")</td><td><%= properties.get("description",String.class) %></td></tr>
            <tr><td>Page Path("path")</td><td><%= properties.get("path",String.class) %></td></tr>
            <tr><td>Page Depth("depth")</td><td><%= properties.get("depth",String.class) %></td></tr>   
        </table>       
        <hr/>
        <!-- currentNode is the current JCR node object, remember
        a page is a node type cq:Page, the objec type is
        javax.jcr.Node interface -->
        <table>
            <tr>
                <td colspan=2><h3>Node builtin properties</h3></th>
            </tr>
            <tr><td>Node Name (getName)</td><td><%= currentNode.getName() %></td></tr>
            <tr><td>Node Path (getPath)</td><td><%= currentNode.getPath() %></td></tr>
            <tr><td>Node Depth (getDepth)</td><td><%= currentNode.getDepth() %></td></tr>
        </table>
        <br/>
        <table>
            <tr>
                <td colspan=2><h3>Node properties</h3></th>
            </tr>
            <tr><td>Node title ("jcr:title")</td><td><%= currentNode.getProperty("jcr:title").getString() %></td></tr>
            <%--  <tr><td>Node title ("title")</td><td><%= currentNode.getProperty("title").getString() %></td></tr> --%>
        </table>          
        <hr/>
        <!-- currentStyle is the current style object of the current cell,
        design properties are inherit by child nodes, the objec type is
        com.day.cq.wcm.api.designer.Style interface -->
        <table>
            <tr>
                <td colspan=2><h3>Style builtin properties</h3></td>
            </tr>
            <tr><td>Style path (getPath)</td><td><%= currentDesign.getCssPath() %></td></tr>
            <tr><td>Style ID (getId)</td><td><%= currentDesign.getId() %></td></tr>
        </table>
    </body>
</html>