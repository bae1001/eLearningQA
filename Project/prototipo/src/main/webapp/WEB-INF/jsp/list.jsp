<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.ubu.lsi.Fachada" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prototipo</title>
</head>
<body>

    <%String username=request.getParameter("username");
      session.setAttribute("username", request.getParameter("username"));
      session.setAttribute("password", request.getParameter("password"));
      Fachada fachada= new Fachada((String)session.getAttribute("username"),(String)session.getAttribute("password"));
      session.setAttribute("fachada", fachada);
      session.setAttribute("token", fachada.conectarse((String)session.getAttribute("username"),(String)session.getAttribute("password")));
      if((String)session.getAttribute("token")=="Invalid login, please try again"){response.sendRedirect("/");}
      String respuesta="";
      try{
        respuesta=fachada.generarListaCursos((String)session.getAttribute("token"));
      }catch(Exception e){
        response.sendRedirect("/");
      }
      %>
<h3>Estas registrado como <%=username%>.</h3>
<p style="text-align: right"><a href="../">Desconectar</a></p>
<p><a href="../informe">Generar informe global</a></p>
<p><%=respuesta%></p>
</body>
</html>