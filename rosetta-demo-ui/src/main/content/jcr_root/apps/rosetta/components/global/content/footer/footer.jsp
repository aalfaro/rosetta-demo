<%@include file="/apps/demo/components/global/global.jsp" %>

<div class="row">
  <div class="footer-col column grid-12">
<p class="footer-links">
<%

String[] urls = currentStyle.get("urls", String[].class);

if(urls != null && urls.length > 0){
	Page footerPage = null;
    for (int i = 0 ; i < urls.length; i++) {
      String url = urls[i];		 
      String linkTitle = "";
      String classAttr = "";
      			 
	  PageManager pm = currentPage.getPageManager();
	  footerPage = pm.getPage(url);	 
	  
	  if (footerPage != null) {
		  linkTitle = footerPage.getTitle();
		  
	      if (i < urls.length-1) {
	          classAttr = " class=\"footer-link\"";
	      }
	      
	      String location = PageUtils.getResolvedPath(url, "", slingRequest, pageContext);

	      %>
	        <a<%= classAttr %> href="<%= location %>" title="<%= linkTitle %>"><%= linkTitle %></a>
	      <%
	      
	  }
}

}else if (WCMMode.fromRequest(request) == WCMMode.EDIT || WCMMode.fromRequest(request) == WCMMode.DESIGN) {
	%>Please insert footer links on the configuration dialog<%
}
%>
</p>
<c:if test="${ ! empty currentStyle.disclaimer }">
    <p class="footer-disclaimer">${ currentStyle.disclaimer }</p>
</c:if>
<c:if test="${ ! empty currentStyle.copyrightyear }">
    <p class="footer-copyright">&copy; ${ currentStyle.copyrightyear }</p>
</c:if>
  </div>
</div>
