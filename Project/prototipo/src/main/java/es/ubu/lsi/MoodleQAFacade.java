package es.ubu.lsi;

import es.ubu.lsi.model.Course;
import es.ubu.lsi.model.Module;
import es.ubu.lsi.model.Resource;
import es.ubu.lsi.model.User;

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
        Course curso= getCursoPorId(token, courseid);
        boolean progresoActivado=isestaProgresoActivado(token,courseid);
        boolean variedadFormatos=isHayVariedadFormatos(token,courseid);
        boolean tienegrupos= isTieneGrupos(token, courseid);
        boolean tareasGrupales=isHayTareasGrupales(token,courseid);
        boolean sonVisiblesCondiciones= isSonVisiblesCondiciones(token, courseid);
        boolean notaMaxConsistente=isEsNotaMaxConsistente(token,courseid);
        boolean recursosActualizados=isEstanActualizadosRecursos(token,courseid);
        boolean fechasCorrectas=isSonFechasCorrectas(token,courseid);
        boolean muestraCriterios=isMuestraCriterios(token,courseid);
        boolean anidamientoAceptable=isAnidamientoCalificadorAceptable(token,courseid);
        boolean alumnosEnGrupos=isAlumnosEnGrupos(token,courseid);
        boolean respondeATiempo=isRespondeATiempo(token,courseid);
        boolean hayRetroalimentacion=isHayRetroalimentacion(token,courseid);
        boolean corregidoATiempo=isEstaCorregidoATiempo(token,courseid);
        boolean ponderacionVisible=isCalificadorMuestraPonderacion(token,courseid);
        boolean respondenFeedbacks=isRespondenFeedbacks(token,courseid);
        boolean usaSurveys=isUsaSurveys(token,courseid);
        List<User> alumnosSinGrupo=WebServiceClient.obtenerAlumnosSinGrupo(token,courseid);
        List<Module> modulosMalFechados=WebServiceClient.obtenerModulosMalFechados(token,courseid);
        List<Resource> recursosDesfasados=WebServiceClient.obtenerRecursosDesfasados(token,courseid);
        int[] puntosComprobaciones = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        if(progresoActivado){contadorDiseno++;puntosComprobaciones[0]++;}
        if(variedadFormatos){contadorDiseno++;puntosComprobaciones[1]++;}
        if(tienegrupos){contadorDiseno++;puntosComprobaciones[2]++;}
        if(tareasGrupales){contadorDiseno++;puntosComprobaciones[3]++;}
        if(sonVisiblesCondiciones){contadorDiseno++;puntosComprobaciones[4]++;}
        if(notaMaxConsistente){contadorDiseno++;puntosComprobaciones[5]++;}
        if(recursosActualizados){contadorImplementacion++;puntosComprobaciones[6]++;}
        if(fechasCorrectas){contadorImplementacion++;puntosComprobaciones[7]++;}
        if(muestraCriterios){contadorImplementacion++;puntosComprobaciones[8]++;}
        if(anidamientoAceptable){contadorImplementacion++;puntosComprobaciones[9]++;}
        if(alumnosEnGrupos){contadorImplementacion++;puntosComprobaciones[10]++;}
        if(respondeATiempo){contadorRealizacion++;puntosComprobaciones[11]++;}
        if(hayRetroalimentacion){contadorRealizacion++;puntosComprobaciones[12]++;}
        if(corregidoATiempo){contadorRealizacion++;puntosComprobaciones[13]++;}
        if(ponderacionVisible){contadorRealizacion++;puntosComprobaciones[14]++;}
        if(respondenFeedbacks){contadorEvaluacion++;puntosComprobaciones[15]++;}
        if(usaSurveys){contadorEvaluacion++;puntosComprobaciones[16]++;}
        contadorTotal=contadorDiseno+contadorImplementacion+contadorRealizacion+contadorEvaluacion;
        checksTotal=checksDiseno+checksImplementacion+checksRealizacion+checksEvaluacion;//Aqui se suman todas las categorias
        return "<h2>Informe: "+curso.getFullname()+"</h2>"+
                "<h3>Matriz de roles y responsabilidades</h3>"+
                generarMatrizRolPerspectiva(puntosComprobaciones,1)+
                "<h3>Informe de fases</h3>"+
                "<table class=\"tg\">"+
                "<tr><td class=\"tg-plgr\">Puntuación general:</td>"+
                    generarCampoRelativo(contadorTotal,checksTotal)+
                "</tr><tr><td class=\"tg-plgr\">Diseño:</td>"+
                    generarCampoRelativo(contadorDiseno,checksDiseno)+
                "</tr><tr><td class=\"tg-ltgr\">Las opciones de progreso del estudiante están activadas</td>"+
                    generarCampoAbsoluto(progresoActivado)+
                "</tr><tr><td class=\"tg-ltgr\">Se proporcionan contenidos en diferentes formatos</td>"+
                    generarCampoAbsoluto(variedadFormatos)+
                "</tr><tr><td class=\"tg-ltgr\">El curso tiene grupos</td>"+
                    generarCampoAbsoluto(tienegrupos)+
                "</tr><tr><td class=\"tg-ltgr\">El curso tiene actividades grupales</td>"+
                    generarCampoAbsoluto(tareasGrupales)+
                "</tr><tr><td class=\"tg-ltgr\">Los estudiantes pueden ver las condiciones necesarias para completar una actividad</td>"+
                    generarCampoAbsoluto(sonVisiblesCondiciones)+
                "</tr><tr><td class=\"tg-ltgr\">Todas las actividades tienen la misma nota máxima en el calificador</td>"+
                    generarCampoAbsoluto(notaMaxConsistente)+
                "</tr><tr><td class=\"tg-plgr\">Implementación:</td>"+
                    generarCampoRelativo(contadorImplementacion,checksImplementacion)+
                "</tr><tr><td class=\"tg-ltgr\">Los recursos están actualizados</td>"+
                    generarCampoAbsoluto(recursosActualizados)+
                "</tr><tr><td class=\"tg-ltgr\">Fechas de apertura y cierre de tareas son correctas</td>"+
                    generarCampoAbsoluto(fechasCorrectas)+
                "</tr><tr><td class=\"tg-ltgr\">Se detallan los criterios de evaluación (rúbricas, ejemplos)</td>"+
                    generarCampoAbsoluto(muestraCriterios)+
                "</tr><tr><td class=\"tg-ltgr\">El calificador no tiene demasiado anidamiento</td>"+
                    generarCampoAbsoluto(anidamientoAceptable)+
                "</tr><tr><td class=\"tg-ltgr\">Los alumnos están divididos en grupos</td>"+
                    generarCampoAbsoluto(alumnosEnGrupos)+
                "</tr><tr><td class=\"tg-plgr\">Realización:</td>"+
                    generarCampoRelativo(contadorRealizacion,checksRealizacion)+
                "<tr><td class=\"tg-ltgr\">El profesor responde en los foros dentro del límite de 48 horas lectivas desde que se plantea la duda</td>"+
                    generarCampoAbsoluto(respondeATiempo)+
                "</tr><tr><td class=\"tg-ltgr\">Se ofrece retroalimentación de las tareas</td>"+
                    generarCampoAbsoluto(hayRetroalimentacion)+
                "</tr><tr><td class=\"tg-ltgr\">Las tareas están calificadas</td>"+
                    generarCampoAbsoluto(corregidoATiempo)+
                "</tr><tr><td class=\"tg-ltgr\">El calificador muestra cómo ponderan las diferentes tareas</td>"+
                    generarCampoAbsoluto(ponderacionVisible)+
                "</tr><tr><td class=\"tg-plgr\">Evaluación:</td>"+
                    generarCampoRelativo(contadorEvaluacion,checksEvaluacion)+
                "</tr><tr><td class=\"tg-ltgr\">La mayoría de alumnos responden a los feedbacks</td>"+
                    generarCampoAbsoluto(respondenFeedbacks)+
                "</tr><tr><td class=\"tg-ltgr\">Se utilizan encuestas de opinión</td>"+
                    generarCampoAbsoluto(usaSurveys)+
                "</tr></table>"+
                "<h3>Aspectos a mejorar</h3>"+
                generarListaMejoras(alumnosSinGrupo,modulosMalFechados,recursosDesfasados);
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

    public String generarMatrizRolPerspectiva(int[] puntosComprobaciones,int numeroCursos){
        int[][] matrizPuntos=new int[][]{
                {3,1,0,0,0,0,0,0,0},
                {3,1,1,3,1,1,0,0,0},
                {3,1,1,0,0,0,0,0,0},
                {3,1,1,0,0,0,0,0,0},
                {3,1,1,0,0,0,0,0,0},
                {3,1,1,0,0,0,0,0,0},
                {3,1,1,3,1,1,0,0,0},
                {1,3,1,1,3,1,0,0,0},
                {3,1,1,3,1,1,0,0,0},
                {3,1,1,0,0,0,3,1,1},
                {0,0,0,1,0,3,1,0,3},
                {1,3,1,1,3,1,0,0,0},
                {1,3,1,1,3,1,0,0,0},
                {1,3,1,1,3,1,0,0,0},
                {1,3,1,1,3,1,0,0,0},
                {1,1,3,1,1,3,1,1,3},
                {1,1,3,1,1,3,1,1,3}
        };
        int[] puntuaciones=new int[9];
        int[] puntuacionesMax=new int[]{34*numeroCursos,26*numeroCursos,19*numeroCursos,17*numeroCursos,20*numeroCursos,17*numeroCursos,6*numeroCursos,3*numeroCursos,10*numeroCursos};
        for(int i=0;i<matrizPuntos.length;i++){
            for(int j=0;j<puntuaciones.length;j++){
                puntuaciones[j]+=matrizPuntos[i][j]*puntosComprobaciones[i];
            }
        }
        return "<table class=\"tg\"><tr><td class=\"tg-plgr\"></td><td class=\"tg-plgr\">Diseñador</td>"+
                "<td class=\"tg-plgr\">Facilitador</td><td class=\"tg-plgr\">Proveedor</td>"+
                "</tr><tr><td class=\"tg-plgr\">Pedagógica</td>"+generarCampoRelativo(puntuaciones[0],puntuacionesMax[0])+
                generarCampoRelativo(puntuaciones[1],puntuacionesMax[1])+generarCampoRelativo(puntuaciones[2],puntuacionesMax[2])+
                "</tr><tr><td class=\"tg-plgr\">Tecnológica</td>"+generarCampoRelativo(puntuaciones[3],puntuacionesMax[3])+
                generarCampoRelativo(puntuaciones[4],puntuacionesMax[4])+generarCampoRelativo(puntuaciones[5],puntuacionesMax[5])+
                "</tr><tr><td class=\"tg-plgr\">Estratégica</td>"+generarCampoRelativo(puntuaciones[6],puntuacionesMax[6])+
                generarCampoRelativo(puntuaciones[7],puntuacionesMax[7])+generarCampoRelativo(puntuaciones[8],puntuacionesMax[8])+
                "</tr></table>";
    }

    public String generarListaMejoras(List<User> alumnosSinGrupo, List<Module> modulosMalFechados, List<Resource> recursosDesfasados){
        StringBuilder listaAspectosAMejorar=new StringBuilder();
        if (alumnosSinGrupo.size()!=0){
            listaAspectosAMejorar.append("<p>Usuarios no asignados a un grupo:</p><ul>");
            for (User alumno:alumnosSinGrupo) {
                listaAspectosAMejorar.append("<li>"+alumno.getFullname()+"</li>");
            }
            listaAspectosAMejorar.append("</ul>");
        }
        if (modulosMalFechados.size()!=0){
            listaAspectosAMejorar.append("<p>Módulos con fechas incorrectas:</p><ul>");
            for (Module modulo:modulosMalFechados) {
                listaAspectosAMejorar.append("<li>"+modulo.getName()+"</li>");
            }
            listaAspectosAMejorar.append("</ul>");
        }
        if (recursosDesfasados.size()!=0){
            listaAspectosAMejorar.append("</ul><p>Recursos sin actualizar:</p><ul>");
            for (Resource recurso:recursosDesfasados) {
                listaAspectosAMejorar.append("<li>"+recurso.getName()+"</li>");
            }
            listaAspectosAMejorar.append("</ul>");
        }
        return listaAspectosAMejorar.toString();
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
