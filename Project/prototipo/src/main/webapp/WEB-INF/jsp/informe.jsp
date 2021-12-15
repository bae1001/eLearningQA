<!DOCTYPE html>
<%@ page import="es.ubu.lsi.Fachada" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prototipo</title>
</head>
<body>
    <%Fachada fachada=(Fachada)session.getAttribute("fachada");
      String courseid= request.getParameter("courseid");
      String informe="";
      if(courseid==null){
        informe=fachada.generarInformeGlobal((String)session.getAttribute("token"));
      }else{
        informe=fachada.generarInformeEspecifico((String)session.getAttribute("token"), Integer.valueOf(courseid));
      }%>

<%=informe%>
</body>
</html>