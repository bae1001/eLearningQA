<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.ubu.lsi.ELearningQAFacade, org.apache.logging.log4j.LogManager, org.apache.logging.log4j.Logger" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>eLearningQA-Informe</title>
    <style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;}
    .tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;
      overflow:hidden;padding:0px 0px;word-break:normal;}
    .tg .tg-plgr{background-color:#C0C0C0;font-weight:bold;text-align:center;vertical-align:middle}
    .tg .tg-ltgr{background-color:#EFEFEF;text-align:right;vertical-align:middle}
    .tg .tg-pgre{background-color:#00FF00;text-align:center;vertical-align:middle}
    .tg .tg-char{background-color:#7FFF00;text-align:center;vertical-align:middle}
    .tg .tg-yell{background-color:#FFFF00;text-align:center;vertical-align:middle}
    .tg .tg-oran{background-color:#FF7F00;text-align:center;vertical-align:middle}
    .tg .tg-pred{background-color:#FF0000;text-align:center;vertical-align:middle}
    </style>
</head>
<body>
    <%String informe="";
      try{ELearningQAFacade fachada=(ELearningQAFacade)session.getAttribute("fachada");
      String courseid= request.getParameter("courseid");
      if(courseid==null){
        informe=fachada.generarInformeGlobal();
      }else{
        informe=fachada.generarInformeEspecifico((String)session.getAttribute("token"), Integer.valueOf(courseid));
      }
      }catch(Exception e){
        Logger LOGGER = LogManager.getLogger();
        LOGGER.error("exception", e);
        response.sendRedirect("");
      }
      %>

<%=informe%>
</body>
</html>