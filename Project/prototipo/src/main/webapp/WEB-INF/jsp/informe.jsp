<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, es.ubu.lsi.ELearningQAFacade, es.ubu.lsi.AlertLog, es.ubu.lsi.RegistryIO, es.ubu.lsi.AnalysisSnapshot, es.ubu.lsi.model.Course, org.apache.logging.log4j.LogManager, org.apache.logging.log4j.Logger" %>
<html lang="en">
<head>
    <%String informe="";
          String matriz="";
          String fases="";
          String nombreCurso="";
          String token=(String)session.getAttribute("token");
          String host=(String)session.getAttribute("host");
          String fullname=(String)session.getAttribute("fullname");
          String grafico="";
          float[] porcentajes;
          AlertLog alertas= new AlertLog();
          int[] puntosComprobaciones;
          int[] puntosCurso;
          String vinculo=(String)session.getAttribute("host")+"/course/view.php?id=";
          try{ELearningQAFacade fachada=(ELearningQAFacade)session.getAttribute("fachada");
          String courseid= request.getParameter("courseid");
          vinculo+=courseid;
          if(courseid==null){
            puntosComprobaciones=new int[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            List<Course> listaCursos=fachada.getListaCursos(token);
            for(Course curso:listaCursos){
              alertas.guardarTitulo(curso.getFullname());
              puntosCurso = fachada.realizarComprobaciones(token, curso.getId(), alertas);
              for(int i=0;i<puntosComprobaciones.length;i++){
                puntosComprobaciones[i]+=puntosCurso[i];
              }
            }
            nombreCurso="Informe general de cursos";
            porcentajes=fachada.calcularPorcentajesMatriz(puntosComprobaciones, listaCursos.size());
            matriz=fachada.generarMatrizRolPerspectiva(porcentajes);
            fases=fachada.generarInformeFases(puntosComprobaciones,listaCursos.size());
          }else{
            Course curso= fachada.getCursoPorId(token, Integer.parseInt(courseid));
            session.setAttribute("course",curso);
            alertas.setCourseid(Integer.parseInt(courseid));
            nombreCurso=curso.getFullname();
            session.setAttribute("coursename",nombreCurso);
            puntosComprobaciones = fachada.realizarComprobaciones(token, Integer.parseInt(courseid), alertas);
            session.setAttribute("puntos",puntosComprobaciones);
            session.setAttribute("user",fullname);
            session.setAttribute("facade",fachada);
            porcentajes=fachada.calcularPorcentajesMatriz(puntosComprobaciones,1);
            matriz=fachada.generarMatrizRolPerspectiva(porcentajes);
            fases=fachada.generarInformeFases(puntosComprobaciones,1);
            RegistryIO.guardarResultados(host, fullname, courseid,
                       new AnalysisSnapshot(nombreCurso, puntosComprobaciones, porcentajes, alertas.toString()));
            grafico=RegistryIO.generarGraficos(host, fullname, courseid);
          }
          }catch(Exception e){
            Logger LOGGER = LogManager.getLogger();
            LOGGER.error("Error en la vista", e);
            response.sendRedirect("error");
          }

          %>
    <meta charset="UTF-8">
    <title><%=nombreCurso%>-Informe</title>
    <link rel="icon" type="image/x-icon" href="Logo.png">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/bootstrap.bundle.min.js"></script>
    <script src="/js/plotly-latest.min.js"></script>
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
          <img src="FullLogo.png" width="200" height="32" alt="eLearningQA">
        </div>
        <div class="btn-group col" role="group">
          <button class="tablink btn btn-primary" onclick="exportExcel()">Exportar excel <img src="excelIcon.png"> </button>
          <button class="tablink btn btn-primary active" style="box-shadow: none;" onclick="openTab(event, 'Fases')">Informe de fases</button>
          <button class="tablink btn btn-primary" style="box-shadow: none;" onclick="openTab(event, 'Matriz')">Evolución del rendimiento</button>
        </div><div class="col m-3 text-center"><a target="_blank" href=<%=vinculo%>><%=nombreCurso%></a></div></header>
                  <div class="d-flex justify-content-center" style="height:85vh;background-image: url('atardecer.jpg');">
                    <div id="Fases" class="tabcontent w-100 p-0" style="display:flex">
                <div class="card m-2 me-0 p-1" style="width: 60%;overflow:auto;">
                  <%=fases%>
                </div>
                <div class="card m-2 ms-0 p-1" style="width: 40%;overflow:auto;">
                  <%=alertas%>
                </div>
        </div>
        <div id="Matriz" class="tabcontent w-100 p-0" style="display:none">
            <div class="card m-3 p-3 w-100">
            <%=matriz%>
            <div id="grafico"></div>
            </div></div>
      </div>
          <footer class="d-flex justify-content-evenly p-3 bg-dark text-white">
            <p><img src="FullLogo.png" width="200" height="32" alt="eLearningQA"></p>

            <a target="_blank" href="./manual">Manual de usuario</a>
            <a target="_blank" href="./about">Acerca de</a>
            <a href="mailto:bae1001@alu.ubu.es">Contacto</a>
          </footer>
          <script>
          <%=grafico%>
          function string2ArrayByte(s) {
            var buf = new ArrayBuffer(s.length);
            var view = new Uint8Array(buf);
            for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
            return buf;
          }

          function exportExcel() {
            $.ajax({
                method: "GET",
                url: "/excel-report",
            }).done(function(excel, statusText, statusCode) {
                if (statusCode.status == 200) {
                const blob = new Blob([string2ArrayByte(atob(excel))], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64' });

                const excelUrl = window.URL.createObjectURL(blob);

                const link = document.createElement('a');

                link.href = excelUrl;
                link.download = 'excel_report_<%=((Course)session.getAttribute("course")).getFullname()%>.xlsx';

                document.body.appendChild(link);
                link.click();

                window.URL.revokeObjectURL(excelUrl);
                } else {
                  swal({
                    title: "Error",
                    text: "No ha sido posible generar el excel",
                    icon: "error",
                  });
                }
            });
          }

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
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
          <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</body>
</html>