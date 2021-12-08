<!DOCTYPE html>
<%@ page import="es.ubu.lsi.WebServiceClient" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prototipo</title>
</head>
<body>
    <h2>Lista de cursos recientes</h2>
    <%session.setAttribute("username", request.getParameter("username"));
      session.setAttribute("password", request.getParameter("password"));
      session.setAttribute("token", WebServiceClient.login((String)session.getAttribute("username"),(String)session.getAttribute("password")));
      /*String token=WebServiceClient.login(request.getParameter("username"),request.getParameter("password"));*/
      String respuesta=WebServiceClient.callFunction("core_course_get_recent_courses",(String)session.getAttribute("token"));%>

<p><%=respuesta%></p>
${course}
<br/>
</c:forEach>
</body>
</html>