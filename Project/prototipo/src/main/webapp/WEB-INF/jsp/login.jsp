<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.FileReader,java.util.Properties"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>MoodleQA-Login</title>
</head>
<body>
    <h2>Login</h2>
    <p style="text-align: right"><a target="_blank" href="../manual">Manual de usuario</a></button></p>
    <%
    FileReader reader = new FileReader("config");
    Properties properties=new Properties();
    properties.load(reader);
    String host=properties.getProperty("host");
    %>
<form action="list" method="post">
  <p>Username: <input type="text" name="username" size="40"></p>
  <p>Password: <input type="password" name="password" size="40"></p>
  <p>Host: <input type="text" name="host" value=<%=host%> size="40"></p>
    <input type="submit" value="Entrar">
    <input type="reset" value="Borrar">
  </p>
</form>
</body>
</html>