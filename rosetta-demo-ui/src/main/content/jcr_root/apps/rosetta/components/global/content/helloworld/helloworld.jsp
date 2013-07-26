<%@include file="/apps/rosetta/components/global/global.jsp" %>

<%@page import="com.rosetta.demo.helloworld.HelloWorldService" %>

<%
HelloWorldService helloWorldService = sling.getService(HelloWorldService.class);
String name = properties.get("name", "Anonymous");
%>

<c:set var="helloWorld" value="<%= helloWorldService.sayHello(name) %>" />
<h1>${helloWorld}</h1>