<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.ubu.lsi.ELearningQAFacade, es.ubu.lsi.model.Course, org.apache.logging.log4j.LogManager, org.apache.logging.log4j.Logger" %>
<html lang="en">
<head>
    <%String informe="";
          String matriz="";
          String fases="";
          String nombreCurso="";
          String token=(String)session.getAttribute("token");
          try{ELearningQAFacade fachada=(ELearningQAFacade)session.getAttribute("fachada");
          String courseid= request.getParameter("courseid");
          if(courseid==null){
            informe=fachada.generarInformeGlobal();
          }else{
            Course curso= fachada.getCursoPorId(token, Integer.parseInt(courseid));
            int[] puntosComprobaciones = fachada.realizarComprobaciones(token, Integer.parseInt(courseid));
            nombreCurso=curso.getFullname();
            matriz=fachada.generarMatrizRolPerspectiva(puntosComprobaciones, 1);
            fases=fachada.generarInformeFasesEspecifico(puntosComprobaciones);
          }
          }catch(Exception e){
            Logger LOGGER = LogManager.getLogger();
            LOGGER.error("exception", e);
            response.sendRedirect("");
          }
          %>
    <meta charset="UTF-8">
    <title><%=nombreCurso%>-Informe</title>
    <link rel="icon" type="image/x-icon" href="Logo.png">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;}
    .tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;
      overflow:hidden;padding:0px 0px;word-break:normal;}
    .tg .tg-plgr{background-color:#C0C0C0;font-weight:bold;text-align:center;vertical-align:middle}
    .tg .tg-ltgr{background-color:#EFEFEF;text-align:right;vertical-align:middle}
    .tg tr.active{-webkit-filter: brightness(90%);border-style:solid;border-width:2px;}
    .tg tr:hover{-webkit-filter: brightness(80%);}
    .tg .tg-pgre{background-color:#198754;text-align:center;vertical-align:middle}
    .tg .tg-char{background-color:#66cc00;text-align:center;vertical-align:middle}
    .tg .tg-yell{background-color:#ffc107;text-align:center;vertical-align:middle}
    .tg .tg-oran{background-color:#fd7e14;text-align:center;vertical-align:middle}
    .tg .tg-pred{background-color:#dc3545;text-align:center;vertical-align:middle}
    tr:nth-child(even) {
      background-color: #efefef;
    }
    tr:nth-child(odd) {
      background-color: #ffffff;
    }

    .tabcontent {
      display: none;
      padding: 6px 6px;
    }

    .info {
      display:none;
      overflow: auto;
    }

    .accordion-button:not(.collapsed){
      color:#842029;background-color:#f8d7da;border-color:#f5c2c7
    }
    </style>
</head>
<body>


      <header class="bg-dark text-white row" style="--bs-gutter-x:0;">
        <div class="col m-3 text-center">
          <img src="FullLogo.png" width="200" height="32">
        </div>
        <div class="btn-group col" role="group">
          <button class="tablink btn btn-primary active" style="box-shadow: none;" onclick="openTab(event, 'Fases')">Informe de fases</button>
          <button class="tablink btn btn-primary" style="box-shadow: none;" onclick="openTab(event, 'Matriz')">Matriz Rol-Responsabilidad</button>
        </div><div class="col m-3 text-center"><%=nombreCurso%></div></header>
                  <div class="d-flex justify-content-center" style="height:85vh;background-image: url('atardecer.jpg');">
                    <div id="Fases" class="tabcontent w-100 p-0" style="display:flex">
                <div class="card m-2 me-0 p-1" style="width: 60%;overflow:auto;">
                    <%=fases%>
                </div>
                              <div class="card m-2 ms-0 p-1" style="width: 40%;overflow:auto;">
                                <div class="alert alert-danger infoline overall design groupactivities">
                                  (Error de muestra WIP) El curso no contiene actividades grupales.
                                </div>
                                <div class="alert alert-danger infoline overall design consistentmaxgrade">
                                  (Error de muestra WIP) Las calificaciones máximas de las actividades y categorías son inconsistentes.
                                </div>
                                <div class="alert alert-danger infoline overall implementation rubrics">
                                  (Error de muestra WIP) No hay métodos avanzados de calificación en ninguna de las actividades.
                                </div>
                                <div class="alert alert-danger p-0 infoline overall implementation studentsingroups"><div class="accordion-item">
                                  <button class="accordion-button alert-danger collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#c1" aria-expanded="true">
                                    (Error de muestra WIP) Hay alumnos sin grupo en el curso.
                                  </button><div id="c1" class="accordion-collapse collapse alert-danger"  >
                                  <div class="accordion-body">
                                    <strong>Alumnos sin grupo</strong></br>
                                    Frances Banks, Mark Ellis, Brian Franklin, Barbara Gardner, Joshua Knight, George Lopez, Anthony Ramirez, Donna Taylor, Gary Vasquez, Brenda Vasquez.
                                  </div>
                                  </div>
                                </div></div>
                                <div class="alert alert-danger infoline overall realization assignmentfeedback">
                                              (Error de muestra WIP) No se hacen suficientes comentarios en las actividades.
                                            </div>
                                            <div class="alert alert-danger p-0 infoline overall realization assignmentsgraded"><div class="accordion-item">
                                              <button class="accordion-button alert-danger collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#c2" aria-expanded="true">
                                                (Error de muestra WIP) Algunas entregas de los alumnos no están corregidas.
                                              </button><div id="c2" class="accordion-collapse collapse alert-danger"  >
                                              <div class="accordion-body">
                                                <strong>Entregas sin corregir</strong></br>
                                                La entrega en Assignment 1 (Text or Audio) por Brian Franklin.</br>
                                                La entrega en Assignment 2 (Upload) por Brian Franklin.</br>
                                                La entrega en Assignment 2 (Upload) por Frances Banks.
                                              </div>
                                              </div>
                                            </div></div>
                                            <div class="alert alert-danger p-0 infoline overall assessment mostrespond"><div class="accordion-item">
                                              <button class="accordion-button alert-danger collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#c3" aria-expanded="true">
                                                (Error de muestra WIP) La mayoría de alumnos no ha respondido algunas encuestas.
                                              </button><div id="c3" class="accordion-collapse collapse alert-danger"  >
                                              <div class="accordion-body">
                                                <strong>Encuestas poco respondidas</strong></br>
                                                Your views on this course
                                              </div>
                                              </div>
                                            </div></div>
                </div>
        </div>
        <div id="Matriz" class="tabcontent w-100 p-0" style="display:none">
            <div class="card m-2 me-0 p-1">
            <%=matriz%>
            </div></div>
      </div>
          <footer class="d-flex justify-content-evenly p-3 bg-dark text-white">
            <p><img src="FullLogo.png" width="200" height="32"></p>

            <a target="_blank" href="../manual">Manual de usuario</a>
            <a>Acerca de</a>
            <a>Contacto</a>
          </footer>
          <script>
          function openTab(evt, tab) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
              tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablink");
            for (i = 0; i < tablinks.length; i++) {
              tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(tab).style.display = "flex";
            evt.currentTarget.className += " active";
          }

          function openInfo(evt, category) {
            var i, ltgr, infolines, wanted;
            ltgr = document.getElementsByTagName("tr");
            for (i = 0; i < ltgr.length; i++) {
              ltgr[i].className = ltgr[i].className.replace(" active", "");
            }
            infolines = document.getElementsByClassName('infoline');
            wanted = [...infolines].filter(element => element.classList.contains(category));
            for (i = 0; i < infolines.length; i++) {
              infolines[i].style.display = "none";
            }
            for (i = 0; i < wanted.length; i++) {
              wanted[i].style.display = "block";
            }
            evt.currentTarget.className += " active";
          }
          </script>
          <script>
          var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
          var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
          return new bootstrap.Tooltip(tooltipTriggerEl)
          })
          </script>

</body>
</html>