<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.ubu.lsi.MoodleQAFacade,es.ubu.lsi.WebServiceClient"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MoodleQA-Lista de cursos</title>
</head>
<body>

    <%String username=request.getParameter("username");
      session.setAttribute("username", request.getParameter("username"));
      session.setAttribute("password", request.getParameter("password"));
      WebServiceClient.setHOST(request.getParameter("host"));
      MoodleQAFacade fachada= new MoodleQAFacade((String)session.getAttribute("username"),(String)session.getAttribute("password"));
      session.setAttribute("fachada", fachada);
      String respuesta="";
      try{
        session.setAttribute("token", fachada.conectarse((String)session.getAttribute("username"),(String)session.getAttribute("password")));
        respuesta=fachada.generarListaCursos((String)session.getAttribute("token"));
      }catch(Exception e){
        response.sendRedirect("");
      }
      %>
<h3>Estas registrado como <%=username%>.</h3>
<p style="text-align: right"><button onclick="myFunction()">Desconectar</button></p>
<p><a target="_blank" href="../informe">Generar informe global</a></p>
<p><%=respuesta%></p>



<script>
function myFunction() {
  window.location.replace("./logout");
}
</script>
</body>
</html>