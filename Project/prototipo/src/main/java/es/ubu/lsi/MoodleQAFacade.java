package es.ubu.lsi;

import es.ubu.lsi.model.Course;

import java.net.URISyntaxException;
import java.util.List;

public class MoodleQAFacade {
    public MoodleQAFacade(String username, String password) {

    }

    public String conectarse(String username, String password){
        return WebServiceClient.login(username, password);
    }

    public String generarListaCursos(String token){
        List<Course> listaCursos= getListaCursos(token);
        String listaEnTabla="<table border=\"1\"><tr><th>Lista de cursos</th></tr>";
        for (Course curso: listaCursos) {
            listaEnTabla+="<tr><td><a target=\"_blank\" href=\"../informe?courseid="+String.valueOf(curso.getId())+"\">"+curso.getFullname()+"<a></td></tr>";
        }
        listaEnTabla+="</table>";
        return listaEnTabla;
    }

    public List<Course> getListaCursos(String token) {
        return WebServiceClient.obtenerCursos(token);
    }

    public String generarInformeEspecifico(String token, int courseid) throws URISyntaxException {
        Course curso= getCursoPorId(token, courseid);
        boolean tienegrupos= isTieneGrupos(token, courseid);
        boolean sonVisiblesCondiciones= isSonVisiblesCondiciones(token, courseid);
        boolean progresoActivado=isestaProgresoActivado(token,courseid);
        boolean corregidoATiempo=isEstaCorregidoATiempo(token,courseid);
        boolean respondeATiempo=isRespondeATiempo(token,courseid);
        boolean usaSurveys=isUsaSurveys(token,courseid);
        boolean alumnosEnGrupos=isAlumnosEnGrupos(token,courseid);
        boolean notaMaxConsistente=isEsNotaMaxConsistente(token,courseid);
        boolean anidamientoAceptable=isAnidamientoCalificadorAceptable(token,courseid);
        boolean hayRetroalimentacion=isHayRetroalimentacion(token,courseid);
        boolean ponderacionVisible=isCalificadorMuestraPonderacion(token,courseid);
        boolean recursosActualizados=isEstanActualizadosRecursos(token,courseid);
        boolean fechasCorrectas=isSonFechasCorrectas(token,courseid);
        boolean respondenFeedbacks=isRespondenFeedbacks(token,courseid);
        boolean tareasGrupales=isHayTareasGrupales(token,courseid);
        boolean muestraCriterios=isMuestraCriterios(token,courseid);
        boolean variedadFormatos=isHayVariedadFormatos(token,courseid);
        int contadorDiseno=0;
        int checksDiseno=6;
        int contadorImplementacion=0;
        int checksImplementacion=5;
        int contadorRealizacion=0;
        int checksRealizacion=4;
        int contadorEvaluacion=0;
        int checksEvaluacion=2;
        int contadorTotal=0;
        int checksTotal=0;
        if(tienegrupos){contadorDiseno++;}
        if(sonVisiblesCondiciones){contadorDiseno++;}
        if(progresoActivado){contadorDiseno++;}
        if(corregidoATiempo){contadorRealizacion++;}
        if(respondeATiempo){contadorRealizacion++;}
        if(usaSurveys){contadorEvaluacion++;}
        if(alumnosEnGrupos){contadorImplementacion++;}
        if(notaMaxConsistente){contadorDiseno++;}
        if(anidamientoAceptable){contadorImplementacion++;}
        if(hayRetroalimentacion){contadorRealizacion++;}
        if(ponderacionVisible){contadorRealizacion++;}
        if(recursosActualizados){contadorImplementacion++;}
        if(fechasCorrectas){contadorImplementacion++;}
        if(respondenFeedbacks){contadorEvaluacion++;}
        if(tareasGrupales){contadorDiseno++;}
        if(muestraCriterios){contadorImplementacion++;}
        if(variedadFormatos){contadorDiseno++;}
        contadorTotal=contadorDiseno+contadorImplementacion+contadorRealizacion+contadorEvaluacion;
        checksTotal=checksDiseno+checksImplementacion+checksRealizacion+checksEvaluacion;//Aqui se suman todas las categorias
        return "<h2>Informe: "+curso.getFullname()+"</h2>"+
                "<table class=\"tg\">"+
                "<tr><td class=\"tg-plgr\">Puntuación general:</td>"+
                    generarCampoRelativo(contadorTotal,checksTotal)+
                "<tr><td class=\"tg-plgr\">Diseño:</td>"+
                    generarCampoRelativo(contadorDiseno,checksDiseno)+
                "<tr><td class=\"tg-ltgr\">Las opciones de progreso del estudiante están activadas</td>"+
                    generarCampoAbsoluto(progresoActivado)+
                "<tr><td class=\"tg-ltgr\">Se proporcionan contenidos en diferentes formatos</td>"+
                    generarCampoAbsoluto(variedadFormatos)+
                "<tr><td class=\"tg-ltgr\">El curso tiene grupos</td>"+
                    generarCampoAbsoluto(tienegrupos)+
                "<tr><td class=\"tg-ltgr\">El curso tiene actividades grupales</td>"+
                    generarCampoAbsoluto(tareasGrupales)+
                "<tr><td class=\"tg-ltgr\">Los estudiantes pueden ver las condiciones necesarias para completar una actividad</td>"+
                    generarCampoAbsoluto(sonVisiblesCondiciones)+
                "<tr><td class=\"tg-ltgr\">Todas las actividades tienen la misma nota máxima en el calificador</td>"+
                    generarCampoAbsoluto(notaMaxConsistente)+
                "<tr><td class=\"tg-plgr\">Implementación:</td>"+
                    generarCampoRelativo(contadorImplementacion,checksImplementacion)+
                "<tr><td class=\"tg-ltgr\">Los recursos están actualizados</td>"+
                    generarCampoAbsoluto(recursosActualizados)+
                "<tr><td class=\"tg-ltgr\">Fechas de apertura y cierre de tareas son correctas</td>"+
                    generarCampoAbsoluto(fechasCorrectas)+
                "<tr><td class=\"tg-ltgr\">Se detallan los criterios de evaluación (rúbricas, ejemplos)</td>"+
                    generarCampoAbsoluto(muestraCriterios)+
                "<tr><td class=\"tg-ltgr\">El calificador no tiene demasiado anidamiento</td>"+
                    generarCampoAbsoluto(anidamientoAceptable)+
                "<tr><td class=\"tg-ltgr\">Los alumnos están divididos en grupos</td>"+
                    generarCampoAbsoluto(alumnosEnGrupos)+
                "<tr><td class=\"tg-plgr\">Realización:</td>"+
                    generarCampoRelativo(contadorRealizacion,checksRealizacion)+
                "<tr><td class=\"tg-ltgr\">El profesor responde en los foros dentro del límite de 48 horas lectivas desde que se plantea la duda</td>"+
                    generarCampoAbsoluto(respondeATiempo)+
                "<tr><td class=\"tg-ltgr\">Se ofrece retroalimentación de las tareas</td>"+
                    generarCampoAbsoluto(hayRetroalimentacion)+
                "<tr><td class=\"tg-ltgr\">Las tareas están calificadas</td>"+
                    generarCampoAbsoluto(corregidoATiempo)+
                "<tr><td class=\"tg-ltgr\">El calificador muestra cómo ponderan las diferentes tareas</td>"+
                    generarCampoAbsoluto(ponderacionVisible)+
                "<tr><td class=\"tg-plgr\">Evaluación:</td>"+
                    generarCampoRelativo(contadorEvaluacion,checksEvaluacion)+
                "<tr><td class=\"tg-ltgr\">La mayoría de alumnos responden a los feedbacks</td>"+
                    generarCampoAbsoluto(respondenFeedbacks)+
                "<tr><td class=\"tg-ltgr\">Se utilizan encuestas de opinión</td>"+
                    generarCampoAbsoluto(usaSurveys)+
                "</table>";
    }

    public boolean isSonVisiblesCondiciones(String token, int courseid) {
        return WebServiceClient.sonVisiblesCondiciones(token, courseid);
    }

    public boolean isTieneGrupos(String token, int courseid) {
        return WebServiceClient.tieneGrupos(token, courseid);
    }

    public Course getCursoPorId(String token, int courseid) {
        return WebServiceClient.obtenerCursoPorId(token, courseid);
    }

    public boolean isestaProgresoActivado(String token, int courseid) {
        return WebServiceClient.estaProgresoActivado(token, courseid);
    }

    public boolean isUsaSurveys(String token, int courseid) {
        return WebServiceClient.usaSurveys(token, courseid);
    }

    public boolean isAlumnosEnGrupos(String token, int courseid) {
        return WebServiceClient.alumnosEnGrupos(token, courseid);
    }

    public boolean isEstaCorregidoATiempo(String token, int courseid) {
        return WebServiceClient.estaCorregidoATiempo(token, courseid);
    }

    public boolean isRespondeATiempo(String token, int courseid) {
        return WebServiceClient.respondeATiempo(token, courseid);
    }

    public boolean isAnidamientoCalificadorAceptable(String token, int courseid) {
        return WebServiceClient.anidamientoCalificadorAceptable(token, courseid);
    }

    public boolean isCalificadorMuestraPonderacion(String token, int courseid) {
        return WebServiceClient.calificadorMuestraPonderacion(token, courseid);
    }

    public boolean isHayRetroalimentacion(String token, int courseid) {
        return WebServiceClient.hayRetroalimentacion(token, courseid);
    }

    public boolean isEsNotaMaxConsistente(String token, int courseid) {
        return WebServiceClient.esNotaMaxConsistente(token, courseid);
    }

    public boolean isEstanActualizadosRecursos(String token, int courseid) {
        return WebServiceClient.estanActualizadosRecursos(token, courseid);
    }

    public boolean isSonFechasCorrectas(String token, int courseid) {
        return WebServiceClient.sonFechasCorrectas(token, courseid);
    }

    public boolean isRespondenFeedbacks(String token, int courseid) {
        return WebServiceClient.respondenFeedbacks(token, courseid);
    }

    public boolean isHayTareasGrupales(String token, int courseid) {
        return WebServiceClient.hayTareasGrupales(token, courseid);
    }

    public boolean isMuestraCriterios(String token, int courseid) {
        return WebServiceClient.muestraCriterios(token, courseid);
    }

    public boolean isHayVariedadFormatos(String token, int courseid) {
        return WebServiceClient.hayVariedadFormatos(token, courseid);
    }

    public float porcentajeFraccion(float numerador, float denominador){
        return numerador/denominador*100;
    };

    public String generarCampoAbsoluto(boolean resultado){
        if (resultado){
            return "<td class=\"tg-pgre\">Sí</td>";
        }else{
            return "<td class=\"tg-pred\">No</td>";
        }
    }

    public String generarCampoRelativo(float numerador, float denominador){
        float resultado= numerador/denominador;
        String campoAMedias="<meter value=\""+numerador+"\" min=\"0\" max=\""+denominador+"\"></meter>"+
                String.format("%.1f",porcentajeFraccion(numerador, denominador))+"%"+"</td>";
        if (resultado<0.2){return "<td class=\"tg-pred\">"+campoAMedias;}
        if (resultado<0.4){return "<td class=\"tg-oran\">"+campoAMedias;}
        if (resultado<0.6){return "<td class=\"tg-yell\">"+campoAMedias;}
        if (resultado<0.8){
            return "<td class=\"tg-char\">"+campoAMedias;
        }else{
            return "<td class=\"tg-pgre\">"+campoAMedias;
        }
    }

    public String generarInformeGlobal(String token){
        List<Course> listaCursos= getListaCursos(token);
        int counter=0;
        for (Course curso: listaCursos) {
            if(isTieneGrupos(token, curso.getId())){
                counter++;
            }
        }
        return "Cursos con grupos:"+String.valueOf(counter)+"/"+String.valueOf(listaCursos.size())+
                "<meter value=\""+String.valueOf(counter)+"\" min=\"0\" max=\""+String.valueOf(listaCursos.size())+"\"></meter>";
    }
}
