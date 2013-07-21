<%@include file="/apps/rosetta/components/global/global.jsp" %>

<%@page import="com.rosetta.demo.components.generic.entity.GenericConfiguration, 
                com.rosetta.demo.components.category.entity.Category" %>

<jsp:useBean id="categoryController" class="com.panasonic.global.bl.components.specifications.controller.IconSpecsController"></jsp:useBean>
<jsp:useBean id="genericConfiguration" class="com.panasonic.global.bl.components.specifications.entity.SpecsConfiguration">
    <jsp:setProperty name="genericConfiguration" property="request" value="<%= request %>" />
    <jsp:setProperty name="genericConfiguration" property="page" value="<%= currentPage %>" />
    <jsp:setProperty name="genericConfiguration" property="resolver" value="<%= resourceResolver %>" />
    <jsp:setProperty name="genericConfiguration" property="pageManager" value="<%= pageManager %>" />
    <jsp:setProperty name="genericConfiguration" property="pageContext" value="<%= pageContext %>" />
    <jsp:setProperty name="genericConfiguration" property="properties" value="${properties}" />
</jsp:useBean>

<c:set var="isEditMode" value="<%= (WCMMode.fromRequest(request) == WCMMode.EDIT || WCMMode.fromRequest(request) == WCMMode.DESIGN) %>" />
<c:set var="topCategories" value="<%= categoryController.getSpecs(genericConfiguration) %>" />

<c:choose>
	<c:when test="${not empty topCategories}">
		<div id="top-cats">
		    <h1>Top categories</h1>
			<ul>
				<c:forEach var="category" items="${topCategories}" varStatus="indexLoop">
					<li>${category.name}</li>
				</c:forEach>
			</ul>
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="${isEditMode}">
			<h1>No top categories found</h1>
		</c:if>
	</c:otherwise>
</c:choose>
