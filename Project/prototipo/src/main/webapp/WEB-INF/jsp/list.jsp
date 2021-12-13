<!DOCTYPE html>
<%@ page import="es.ubu.lsi.Fachada" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prototipo</title>
</head>
<body>
    <h2>Lista de cursos recientes</h2>
    <%session.setAttribute("username", request.getParameter("username"));
      session.setAttribute("password", request.getParameter("password"));
      Fachada fachada= new Fachada((String)session.getAttribute("username"),(String)session.getAttribute("password"));
      session.setAttribute("fachada", fachada);
      session.setAttribute("token", fachada.conectarse((String)session.getAttribute("username"),(String)session.getAttribute("password")));
      String respuesta=fachada.generarListaCursos((String)session.getAttribute("token"));%>

<p><%=respuesta%></p>
</body>
</html>