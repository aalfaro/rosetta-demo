<%@include file="/apps/rosetta/components/global/global.jsp" %>
<%
String searchIn = currentStyle.get("searchIn", "");
String location = PageUtils.getResolvedPath(currentStyle.get("resultsURL", ""), "#", slingRequest, pageContext);
%>
<cq:setContentBundle/>
<fmt:message key="searchLabel" var="searchLabel"/>
<form class="search" id="frmSearch" action="<%=location %>" method="get">
    <label for="search">${searchLabel}</label>
    <input id="search" name="q" value="${searchLabel}" />
    <input type="hidden" name ="searchIn" value="<%= searchIn %>" />
    <input type="hidden" name="_charset_" value="utf-8" />
    <button type="submit"><fmt:message key="searchSubmit"/></button>
</form>
