<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.FileReader,java.util.Properties"%>
<html lang="en">
<head>
  <title>eLearningQA-Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="/js/bootstrap.bundle.min.js"></script>
</head>
<body>
  <header class="p-3 bg-dark text-white row" style="--bs-gutter-x:0;"><div class="col">(Logo sin texto) eLearningQA</div><div class="col text-end">No estás registrado</div></header>
            <div class="d-flex justify-content-center" style="background-image: url('atardecer.jpg');height: 100vh;">
            <%
                FileReader reader = new FileReader("config");
                Properties properties=new Properties();
                properties.load(reader);
                String host=properties.getProperty("host");
                %>

          <div class="card w-25 p-3 align-self-center" style="min-width: 300px">
                    <h2 class="text-primary">eLearningQA - Login</h2>
                    <form action="list" method="post">
                        <div class="form-floating mb-3 mt-3">
                            <input type="text" class="form-control" id="user" placeholder="Enter username" name="username">
                            <label class="text-primary" for="user">Username</label>
                        </div>
                        <div class="form-floating mt-3 mb-3">
                            <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password">
                            <label class="text-primary" for="pwd">Password</label>
                        </div>
                        <div class="form-floating mb-3 mt-3">
                            <input type="text" class="form-control" id="host" placeholder="Enter host to connect to" name="host" value=<%=host%>>
                            <label class="text-primary" for="host">Host</label>
                        </div>
                    <button type="submit" class="btn btn-primary">Entrar</button>
                </form>
          </div>
    </div>
    <footer class="d-flex justify-content-evenly p-3 bg-dark text-white">
      <p>(Logo completo)</p>

      <a target="_blank" href="../manual">Manual de usuario</a>
      <a>Acerca de</a>
      <a>Contacto</a>
    </footer>
</body>
</html>
