<%@include file="/apps/demo/components/global/global.jsp" %>

    <div class="secondary-logo">
    	<c:set scope="request" var="logoName" value="secondaryLogo"
    	/><c:set scope="request" var="logoAltParam" value="logoAlt2"
    	/><c:set scope="request" var="logoAlt" value="${ currentStyle.logoAlt2 }"
    	/><c:set scope="request" var="logoTitle" value="${ currentStyle.logoTitle2 }"
    	/><c:set scope="request" var="logoURL" value="${ currentStyle.logoURL2 }"
    	/><cq:include script="../utils/render.logo.jsp" />
    </div>
    <c:if test="${ currentStyle.showSearchbox }">
      <cq:include path="search" resourceType="demo/components/global/content/search"/>
    </c:if>
    <div class="primary-logo">
    	<c:set scope="request" var="logoName" value="primaryLogo"
    	/><c:set scope="request" var="logoAltParam" value="logoAlt"
    	/><c:set scope="request" var="logoAlt" value="${ currentStyle.logoAlt }"
    	/><c:set scope="request" var="logoTitle" value="${ currentStyle.logoTitle }"
    	/><c:set scope="request" var="logoURL" value="${ currentStyle.logoURL }"
    	/><cq:include script="../utils/render.logo.jsp" />
    </div>
