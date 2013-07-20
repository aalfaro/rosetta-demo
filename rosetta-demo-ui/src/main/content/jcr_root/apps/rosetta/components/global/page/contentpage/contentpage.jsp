<%@include file="/apps/demo/components/global/global.jsp" %><%

    // read the redirect target from the 'page properties' and perform the
    // redirect if WCM is disabled.
    String location = PageUtils.getResolvedPath(properties.get(PageUtils.STR_REDIRECT_TARGET, ""), "", slingRequest, pageContext);
    
    if (WCMMode.fromRequest(request) != WCMMode.EDIT && location.length() > 0) {
        // check for recursion
        if (!location.equals(currentPage.getPath())) {
            response.sendRedirect(location);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        return;
    }
    // set doctype
    currentDesign.getDoctype(currentStyle).toRequest(request);
    //determine the language from the page properties set for the site
    String languageCode = PageUtils.getPageLanguage(currentPage).getLanguage();

    if (Doctype.fromRequest(request) != null) {
%><%= Doctype.fromRequest(request).getDeclaration()%><%
    } else {
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%=languageCode%>" xml:lang="<%=languageCode%>">
<cq:include script="head.jsp"/>
<cq:include script="body.jsp"/>
</html>
