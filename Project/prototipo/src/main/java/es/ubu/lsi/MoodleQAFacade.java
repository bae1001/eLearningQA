package es.ubu.lsi;

import es.ubu.lsi.model.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

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
        List<User> listaUsuarios= WebServiceClient.obtenerUsuarios(token, courseid);
        StatusList listaEstados=WebServiceClient.obtenerListaEstados(token, courseid, listaUsuarios);
        List<Module> listaModulos=WebServiceClient.obtenerListaModulos(token, courseid);
        List<Group> listaGrupos=WebServiceClient.obtenerListaGrupos(token, courseid);
        List<Assignment> listaTareas=WebServiceClient.obtenerListaTareas(token, courseid);
        List<Table> listaCalificadores=WebServiceClient.obtenerCalificadores(token, courseid);
        List<Resource> listaRecursos=WebServiceClient.obtenerRecursos(token, courseid);
        List<CourseModule> listaModulosTareas=WebServiceClient.obtenerModulosTareas(token, listaTareas);
        List<Post> listaPosts=WebServiceClient.obtenerListaPosts(token, courseid);
        Map<Integer,Integer> mapaFechasLimite=WebServiceClient.generarMapaFechasLimite(listaTareas);
        List<Assignment> tareasConNotas=WebServiceClient.obtenerTareasConNotas(token,mapaFechasLimite);
        List<ResponseAnalysis> listaAnalisis=WebServiceClient.obtenerAnalisis(token, courseid);
        List<Survey> listaSurveys=WebServiceClient.obtenerSurveys(token, courseid);
        List<User> alumnosSinGrupo=WebServiceClient.obtenerAlumnosSinGrupo(WebServiceClient.obtenerUsuarios(token, courseid));
        List<Module> modulosMalFechados=WebServiceClient.obtenerModulosMalFechados(curso, listaModulos);
        List<Resource> recursosDesfasados=WebServiceClient.obtenerRecursosDesfasados(curso, listaRecursos);
        boolean progresoActivado=isestaProgresoActivado(listaEstados);
        boolean variedadFormatos=isHayVariedadFormatos(listaModulos);
        boolean tienegrupos= isTieneGrupos(listaGrupos);
        boolean tareasGrupales=isHayTareasGrupales(listaTareas);
        boolean sonVisiblesCondiciones= isSonVisiblesCondiciones(curso);
        boolean notaMaxConsistente=isEsNotaMaxConsistente(listaCalificadores);
        boolean recursosActualizados=isEstanActualizadosRecursos(recursosDesfasados);
        boolean fechasCorrectas=isSonFechasCorrectas(modulosMalFechados);
        boolean muestraCriterios=isMuestraCriterios(listaModulosTareas);
        boolean anidamientoAceptable=isAnidamientoCalificadorAceptable(listaCalificadores);
        boolean alumnosEnGrupos=isAlumnosEnGrupos(listaUsuarios);
        boolean respondeATiempo=isRespondeATiempo(listaUsuarios,listaPosts);
        boolean hayRetroalimentacion=isHayRetroalimentacion(listaCalificadores);
        boolean corregidoATiempo=isEstaCorregidoATiempo(tareasConNotas,mapaFechasLimite);
        boolean ponderacionVisible=isCalificadorMuestraPonderacion(listaCalificadores);
        boolean respondenFeedbacks=isRespondenFeedbacks(listaAnalisis,listaUsuarios);
        boolean usaSurveys=isUsaSurveys(listaSurveys);
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

    public boolean isSonVisiblesCondiciones(Course curso) {
        return WebServiceClient.sonVisiblesCondiciones(curso);
    }

    public boolean isTieneGrupos(List<Group> listaGrupos) {
        return WebServiceClient.tieneGrupos(listaGrupos);
    }

    public Course getCursoPorId(String token, int courseid) {
        return WebServiceClient.obtenerCursoPorId(token, courseid);
    }



    public boolean isestaProgresoActivado(StatusList listaEstados) {
        return WebServiceClient.estaProgresoActivado(listaEstados);
    }

    public boolean isUsaSurveys(List<Survey> listaEncuestas) {
        return WebServiceClient.usaSurveys(listaEncuestas);
    }

    public boolean isAlumnosEnGrupos(List<User> listaUsuarios) {
        return WebServiceClient.alumnosEnGrupos(listaUsuarios);
    }

    public boolean isEstaCorregidoATiempo(List<Assignment> tareasConNotas, Map<Integer, Integer> mapaFechasLimite) {
        return WebServiceClient.estaCorregidoATiempo(tareasConNotas, mapaFechasLimite);
    }

    public boolean isRespondeATiempo(List<User> listaUsuarios, List<Post> listaPostsCompleta) {
        return WebServiceClient.respondeATiempo(listaUsuarios, listaPostsCompleta);
    }

    public boolean isAnidamientoCalificadorAceptable(List<Table> listaCalificadores) {
        return WebServiceClient.anidamientoCalificadorAceptable(listaCalificadores);
    }

    public boolean isCalificadorMuestraPonderacion(List<Table> listaCalificadores) {
        return WebServiceClient.calificadorMuestraPonderacion(listaCalificadores);
    }

    public boolean isHayRetroalimentacion(List<Table> listaCalificadores) {
        return WebServiceClient.hayRetroalimentacion(listaCalificadores);
    }

    public boolean isEsNotaMaxConsistente(List<Table> listaCalificadores) {
        return WebServiceClient.esNotaMaxConsistente(listaCalificadores);
    }

    public boolean isEstanActualizadosRecursos(List<Resource> listaRecursosDesfasados) {
        return WebServiceClient.estanActualizadosRecursos(listaRecursosDesfasados);
    }

    public boolean isSonFechasCorrectas(List<Module> listaModulosMalFechados) {
        return WebServiceClient.sonFechasCorrectas(listaModulosMalFechados);
    }

    public boolean isRespondenFeedbacks(List<ResponseAnalysis> listaAnalisis, List<User> listaUsuarios) {
        return WebServiceClient.respondenFeedbacks(listaAnalisis,listaUsuarios);
    }

    public boolean isHayTareasGrupales(List<Assignment> listaTareas) {
        return WebServiceClient.hayTareasGrupales(listaTareas);
    }

    public boolean isMuestraCriterios(List<CourseModule> listaModulosTareas) {
        return WebServiceClient.muestraCriterios(listaModulosTareas);
    }

    public boolean isHayVariedadFormatos(List<Module> listamodulos) {
        return WebServiceClient.hayVariedadFormatos(listamodulos);
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
        if (resultado<0.8){return "<td class=\"tg-char\">"+campoAMedias;}
        else{return "<td class=\"tg-pgre\">"+campoAMedias;}
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
        return "Esta característica no está implementada";
    }
}
