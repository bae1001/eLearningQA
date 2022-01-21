package es.ubu.lsi;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ubu.lsi.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoodleQAFacade {
    public static int checksDiseno=6;
    public static int checksImplementacion=5;
    public static int checksRealizacion=4;
    public static int checksEvaluacion=2;
    public static int checksTotal=checksDiseno+checksImplementacion+checksRealizacion+checksEvaluacion;
    public static String[] camposInformeFases;

    public MoodleQAFacade(String username, String password) {
        String sep= File.separator;
        String ruta="."+sep+"src"+sep+"main"+sep+"resources"+sep+"json"+sep+"informe"+sep;
        String extension=".json";
        ObjectMapper mapper=new ObjectMapper();
        try {
            camposInformeFases= mapper.readValue(new File(ruta+"CamposInformeFases"+extension), String[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String generarInformeEspecifico(String token, int courseid) {
        Course curso= getCursoPorId(token, courseid);
        int[] puntosComprobaciones = realizarComprobaciones(token, courseid);
        return "<h2>Informe: " + curso.getFullname() + "</h2>" +
                "<h3>Matriz de roles y responsabilidades</h3>" +
                generarMatrizRolPerspectiva(puntosComprobaciones, 1) +
                "<h3>Informe de fases</h3>" +
                generarInformeFasesEspecifico(puntosComprobaciones)/* +
                "<h3>Aspectos a mejorar</h3>" +
                generarListaMejoras(alumnosSinGrupo, modulosMalFechados, recursosDesfasados)*/;
    }

    private int[] realizarComprobaciones(String token, int courseid) {
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
        int[] puntosComprobaciones = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        if(isestaProgresoActivado(listaEstados)){puntosComprobaciones[0]++;}
        if(isHayVariedadFormatos(listaModulos)){puntosComprobaciones[1]++;}
        if(isTieneGrupos(listaGrupos)){puntosComprobaciones[2]++;}
        if(isHayTareasGrupales(listaTareas)){puntosComprobaciones[3]++;}
        if(isSonVisiblesCondiciones(curso)){puntosComprobaciones[4]++;}
        if(isEsNotaMaxConsistente(listaCalificadores)){puntosComprobaciones[5]++;}
        if(isEstanActualizadosRecursos(recursosDesfasados)){puntosComprobaciones[6]++;}
        if(isSonFechasCorrectas(modulosMalFechados)){puntosComprobaciones[7]++;}
        if(isMuestraCriterios(listaModulosTareas)){puntosComprobaciones[8]++;}
        if(isAnidamientoCalificadorAceptable(listaCalificadores)){puntosComprobaciones[9]++;}
        if(isAlumnosEnGrupos(listaUsuarios)){puntosComprobaciones[10]++;}
        if(isRespondeATiempo(listaUsuarios,listaPosts)){puntosComprobaciones[11]++;}
        if(isHayRetroalimentacion(listaCalificadores)){puntosComprobaciones[12]++;}
        if(isEstaCorregidoATiempo(tareasConNotas,mapaFechasLimite)){puntosComprobaciones[13]++;}
        if(isCalificadorMuestraPonderacion(listaCalificadores)){puntosComprobaciones[14]++;}
        if(isRespondenFeedbacks(listaAnalisis,listaUsuarios)){puntosComprobaciones[15]++;}
        if(isUsaSurveys(listaSurveys)){puntosComprobaciones[16]++;}
        return puntosComprobaciones;
    }

    private String generarInformeFasesEspecifico(int[] puntos) {
        int contadorDiseno=puntos[0]+puntos[1]+puntos[2]+puntos[3]+puntos[4]+puntos[5];
        int contadorImplementacion=puntos[6]+puntos[7]+puntos[8]+puntos[9]+puntos[10];
        int contadorRealizacion=puntos[11]+puntos[12]+puntos[13]+puntos[14];
        int contadorEvaluacion=puntos[15]+puntos[16];
        int contadorTotal=contadorDiseno+contadorImplementacion+contadorRealizacion+contadorEvaluacion;
        return camposInformeFases[0]+generarCampoRelativo(contadorTotal, checksTotal) +
                camposInformeFases[1]+generarCampoRelativo(contadorDiseno, checksDiseno) +
                camposInformeFases[2]+generarCampoAbsoluto(puntos[0]) +
                camposInformeFases[3]+generarCampoAbsoluto(puntos[1]) +
                camposInformeFases[4]+generarCampoAbsoluto(puntos[2]) +
                camposInformeFases[5]+generarCampoAbsoluto(puntos[3]) +
                camposInformeFases[6]+generarCampoAbsoluto(puntos[4]) +
                camposInformeFases[7]+generarCampoAbsoluto(puntos[5]) +
                camposInformeFases[8]+generarCampoRelativo(contadorImplementacion, checksImplementacion) +
                camposInformeFases[9]+generarCampoAbsoluto(puntos[6]) +
                camposInformeFases[10]+generarCampoAbsoluto(puntos[7]) +
                camposInformeFases[11]+generarCampoAbsoluto(puntos[8]) +
                camposInformeFases[12]+generarCampoAbsoluto(puntos[9]) +
                camposInformeFases[13]+generarCampoAbsoluto(puntos[10]) +
                camposInformeFases[14]+generarCampoRelativo(contadorRealizacion, checksRealizacion) +
                camposInformeFases[15]+generarCampoAbsoluto(puntos[11]) +
                camposInformeFases[16]+generarCampoAbsoluto(puntos[12]) +
                camposInformeFases[17]+generarCampoAbsoluto(puntos[13]) +
                camposInformeFases[18]+generarCampoAbsoluto(puntos[14]) +
                camposInformeFases[19]+generarCampoRelativo(contadorEvaluacion, checksEvaluacion) +
                camposInformeFases[20]+generarCampoAbsoluto(puntos[15]) +
                camposInformeFases[21]+generarCampoAbsoluto(puntos[16])+camposInformeFases[22];
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

    public String generarCampoAbsoluto(int puntos){
        if (puntos==0){
            return "<td class=\"tg-pred\">No</td>";
        }else{
            return "<td class=\"tg-pgre\">Sí</td>";
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

    public String generarMatrizRolPerspectiva(int[] puntos,int numeroCursos){
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
                puntuaciones[j]+=matrizPuntos[i][j]*puntos[i];
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
