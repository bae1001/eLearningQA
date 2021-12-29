<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.ubu.lsi.Fachada" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MoodleQA-Informe</title>
</head>
<body>
    <%String informe="";
      try{Fachada fachada=(Fachada)session.getAttribute("fachada");
      String courseid= request.getParameter("courseid");
      if(courseid==null){
        informe=fachada.generarInformeGlobal((String)session.getAttribute("token"));
      }else{
        informe=fachada.generarInformeEspecifico((String)session.getAttribute("token"), Integer.valueOf(courseid));
      }
      }catch(Exception e){
        response.sendRedirect("");
      }
      %>

<%=informe%>
</body>
</html>