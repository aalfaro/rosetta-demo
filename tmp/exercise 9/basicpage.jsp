<%--

  (Training) Basicpage component.

  (Training) Basicpage page component.

  Is using the base for all "page" components. Basically includes the "head"
  and the "body" scripts.

=======================================================================
--%><%@page session="false"
            contentType="text/html; charset=utf-8"
            import="com.day.cq.commons.Doctype,
                    com.day.cq.wcm.api.WCMMode,
                    com.day.cq.wcm.foundation.ELEvaluator" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%>
<!--  no need to include all taglibs just CQLib -->
<cq:defineObjects/>
<html>
<cq:include script="head.jsp"/>
<cq:include script="body.jsp"/>
</html>