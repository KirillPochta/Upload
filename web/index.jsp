
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="helpers.FileHandler" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  index
  <p>index</p>
  <%--      <p>Init param value: <%= application.getInitParameter("doc-dir")%></p>--%>
  <a href="Sss">Sss</a>

  <ul>
    <%
      FileHandler fileHandler = new FileHandler(".docx", application.getInitParameter("doc-dir"));
      File[] files = fileHandler.GetFiles();
      for(File file:files) {
    %>
    <li><a href="Sss?file=<%= file.getName()%>"><%= file.getName()%></a></li>
    <%
      }
    %>
  </ul>

  <form method="post" action="Sss" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <button type="submit">Upload</button>
  </form>
  </body>
</html>
