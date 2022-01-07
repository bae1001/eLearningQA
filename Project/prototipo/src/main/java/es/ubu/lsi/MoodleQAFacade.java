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
                "<table border=\"1\">"+
                "<tr><td><p style=\"text-align: right\">Puntuación general:</p></td><td>"+
                    "<meter value=\""+ contadorTotal +"\" min=\"0\" max=\""+ checksTotal +"\"></meter>"+
                        String.format("%.2f",porcentajeFraccion(contadorTotal, checksTotal)) +"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Diseño:</p></td><td>"+
                    "<meter value=\""+ contadorDiseno +"\" min=\"0\" max=\""+ checksDiseno +"\"></meter>"+
                        String.format("%.2f",porcentajeFraccion(contadorDiseno, checksDiseno)) +"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Las opciones de progreso del estudiante están activadas</p></td><td>"+
                    (progresoActivado?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Se proporcionan contenidos en diferentes formatos</p></td><td>"+
                    (variedadFormatos?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El curso tiene grupos</p></td><td>"+
                    (tienegrupos?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El curso tiene actividades grupales</p></td><td>"+
                    (tareasGrupales?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Los estudiantes pueden ver las condiciones necesarias para completar una actividad</p></td><td>"+
                    (sonVisiblesCondiciones?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Todas las actividades tienen la misma nota máxima en el calificador</p></td><td>"+
                    (notaMaxConsistente?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Implementación:</p></td><td>"+
                    "<meter value=\""+ contadorImplementacion +"\" min=\"0\" max=\""+ checksImplementacion +"\"></meter>"+
                        String.format("%.2f",porcentajeFraccion(contadorImplementacion, checksImplementacion)) +"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Los recursos están actualizados</p></td><td>"+
                    (recursosActualizados?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Fechas de apertura y cierre de tareas son correctas</p></td><td>"+
                    (fechasCorrectas?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Se detallan los criterios de evaluación (rúbricas, ejemplos)</p></td><td>"+
                    (muestraCriterios?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El calificador no tiene demasiado anidamiento</p></td><td>"+
                    (anidamientoAceptable?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Los alumnos están divididos en grupos</p></td><td>"+
                    (alumnosEnGrupos?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Realización:</p></td><td>"+
                    "<meter value=\""+ contadorRealizacion +"\" min=\"0\" max=\""+ checksRealizacion +"\"></meter>"+
                        String.format("%.2f",porcentajeFraccion(contadorRealizacion, checksRealizacion)) +"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">El profesor responde en los foros dentro del límite de 48 horas lectivas desde que se plantea la duda</p></td><td>"+
                    (respondeATiempo?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Se ofrece retroalimentación de las tareas</p></td><td>"+
                    (hayRetroalimentacion?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Las tareas están calificadas</p></td><td>"+
                    (corregidoATiempo?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El calificador muestra cómo ponderan las diferentes tareas</p></td><td>"+
                    (ponderacionVisible?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Evaluación:</p></td><td>"+
                    "<meter value=\""+ contadorEvaluacion +"\" min=\"0\" max=\""+ checksEvaluacion +"\"></meter>"+
                        String.format("%.2f",porcentajeFraccion(contadorEvaluacion, checksEvaluacion)) +"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">La mayoría de alumnos responden a los feedbacks</p></td><td>"+
                    (respondenFeedbacks?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Se utilizan encuestas de opinión</p></td><td>"+
                    (usaSurveys?"Si":"No")+"</td>"+
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
