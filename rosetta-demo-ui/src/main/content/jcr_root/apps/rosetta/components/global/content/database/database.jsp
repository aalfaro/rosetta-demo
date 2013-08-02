<%@include file="/apps/rosetta/components/global/global.jsp" %>

<%@ page import="com.day.commons.datasource.poolservice.DataSourcePool" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet"%>
<%
DataSourcePool dspService = sling.getService(DataSourcePool.class);
  try {
     DataSource ds = (DataSource) dspService.getDataSource("mssqlds");   
     if(ds != null) {
         %><p>Obtained the datasource!</p><%
         %><%final Connection connection = ds.getConnection();
          final Statement statement = connection.createStatement();
          final ResultSet resultSet = statement.executeQuery("SELECT * from dbo.product"); 
          int r=0;
          while(resultSet.next()){
             r=r+1;
          } 
          resultSet.close();
          %><p>Number of results: <%=r%></p><%
      } 
   }catch (Exception e) {
        %><p>error! <%=e.getMessage()%></p><%
    } 
%>