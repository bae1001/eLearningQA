<!DOCTYPE html>
<%@ page import="es.ubu.lsi.WebServiceClient" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prototipo</title>
</head>
<body>
    <h2>Lista de cursos recientes</h2>
    <%String token=WebServiceClient.login(request.getParameter("username"),request.getParameter("password"));
      String respuesta=WebServiceClient.callFunction("core_course_get_recent_courses",token);%>

<p><%=respuesta%></p>
${course}
<br/>
</c:forEach>
</body>
</html>