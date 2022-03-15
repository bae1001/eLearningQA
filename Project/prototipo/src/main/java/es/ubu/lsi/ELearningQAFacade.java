package es.ubu.lsi;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ubu.lsi.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ELearningQAFacade {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CHECKS_DISENO =6;
    private static final int CHECKS_IMPLEMENTACION =5;
    private static final int CHECKS_REALIZACION =4;
    private static final int CHECKS_EVALUACION =2;
    private static final int CHECKS_TOTAL = CHECKS_DISENO + CHECKS_IMPLEMENTACION + CHECKS_REALIZACION + CHECKS_EVALUACION;
    protected static String[] camposInformeFases;

    static {
        String sep= File.separator;
        String ruta="."+sep+"src"+sep+"main"+sep+"resources"+sep+"json"+sep+"informe"+sep;
        String extension=".json";
        ObjectMapper mapper=new ObjectMapper();
        try {
            camposInformeFases= mapper.readValue(new File(ruta+"CamposInformeFases"+extension), String[].class);
        } catch (Exception e) {
            LOGGER.error("exception", e);
        }
    }

    public ELearningQAFacade() {
        //El constructor está vacío pero en un futuro podría necesitar atributos
    }

    public String conectarse(String username, String password){
        return WebServiceClient.login(username, password);
    }

    public String generarListaCursos(String token){
        List<Course> listaCursos= getListaCursos(token);
        StringBuilder listaEnTabla= new StringBuilder("<table>");
        for (Course curso: listaCursos) {
            listaEnTabla.append("<tr><td><a target=\"_blank\" href=\"../informe?courseid=").append(curso.getId()).append("\">").append(curso.getFullname()).append("</a></td></tr>");
        }
        listaEnTabla.append("</table>");
        return listaEnTabla.toString();
    }

    public List<Course> getListaCursos(String token) {
        return WebServiceClient.obtenerCursos(token);
    }

    public String obtenerNombreCompleto(String token, String username) {
        return WebServiceClient.obtenerNombreCompleto(token, username);
    }

    public String generarInformeEspecifico(String token, int courseid) {
        Course curso= getCursoPorId(token, courseid);
        int[] puntosComprobaciones = realizarComprobaciones(token, courseid);
        return "<h2>Informe: " + curso.getFullname() + "</h2>" +
                "<h3>Matriz de roles y responsabilidades</h3>" +
                generarMatrizRolPerspectiva(puntosComprobaciones, 1) +
                "<h3>Informe de fases</h3>" +
                generarInformeFasesEspecifico(puntosComprobaciones);
    }

    public int[] realizarComprobaciones(String token, int courseid) {
        Course curso= getCursoPorId(token, courseid);
        List<User> listaUsuarios= WebServiceClient.obtenerUsuarios(token, courseid);
        StatusList listaEstados=WebServiceClient.obtenerListaEstados(token, courseid, listaUsuarios);
        List<es.ubu.lsi.model.Module> listaModulos=WebServiceClient.obtenerListaModulos(token, courseid);
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
        List<es.ubu.lsi.model.Module> modulosMalFechados=WebServiceClient.obtenerModulosMalFechados(curso, listaModulos);
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

    public String generarInformeFasesEspecifico(int[] puntos) {
        int contadorDiseno=puntos[0]+puntos[1]+puntos[2]+puntos[3]+puntos[4]+puntos[5];
        int contadorImplementacion=puntos[6]+puntos[7]+puntos[8]+puntos[9]+puntos[10];
        int contadorRealizacion=puntos[11]+puntos[12]+puntos[13]+puntos[14];
        int contadorEvaluacion=puntos[15]+puntos[16];
        int contadorTotal=contadorDiseno+contadorImplementacion+contadorRealizacion+contadorEvaluacion;
        return camposInformeFases[0]+generarCampoRelativo(contadorTotal, CHECKS_TOTAL) +
                camposInformeFases[1]+generarCampoRelativo(contadorDiseno, CHECKS_DISENO) +
                generarFilas(2, 0, 6, puntos)+
                camposInformeFases[8]+generarCampoRelativo(contadorImplementacion, CHECKS_IMPLEMENTACION) +
                generarFilas(9, 6, 5, puntos)+
                camposInformeFases[14]+generarCampoRelativo(contadorRealizacion, CHECKS_REALIZACION) +
                generarFilas(15, 11, 4, puntos)+
                camposInformeFases[19]+generarCampoRelativo(contadorEvaluacion, CHECKS_EVALUACION) +
                generarFilas(20, 15, 2, puntos)+camposInformeFases[22];
    }

    private String generarFilas(int origen1, int origen2, int cantidad, int[] puntos){
        StringBuilder filas= new StringBuilder();
        for (int i = 0;i<cantidad;i++){
            filas.append(camposInformeFases[origen1 + i]).append(generarCampoAbsoluto(puntos[origen2 + i]));
        }
        return filas.toString();
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

    public boolean isSonFechasCorrectas(List<es.ubu.lsi.model.Module> listaModulosMalFechados) {
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

    public boolean isHayVariedadFormatos(List<es.ubu.lsi.model.Module> listamodulos) {
        return WebServiceClient.hayVariedadFormatos(listamodulos);
    }

    public float porcentajeFraccion(float numerador, float denominador){
        return numerador/denominador*100;
    }

    public String generarCampoAbsoluto(boolean resultado){
        if (resultado){
            return "<td class=\"tg-pgre\">Sí</td>";
        }else{
            return "<td class=\"tg-pred\">No</td>";
        }
    }

    public String generarCampoAbsoluto(int puntos){
        if (puntos==0){
            return "<td class=\"tg-pred\"><img src=\"Cross.png\" width=\"16\" height=\"16\"></td>";
        }else{
            return "<td class=\"tg-pgre\"><img src=\"Check.png\" width=\"16\" height=\"16\"></td>";
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


    public String generarInformeGlobal(){
        return "Esta característica no está implementada";
    }
}
