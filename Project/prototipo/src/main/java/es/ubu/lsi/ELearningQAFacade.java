package es.ubu.lsi;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ubu.lsi.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ELearningQAFacade {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CHECKS_DISENO = 8;
    private static final int CHECKS_IMPLEMENTACION = 5;
    private static final int CHECKS_REALIZACION = 7;
    private static final int CHECKS_EVALUACION = 2;
    private static final int CHECKS_TOTAL = CHECKS_DISENO + CHECKS_IMPLEMENTACION + CHECKS_REALIZACION
            + CHECKS_EVALUACION;

    protected static String[] camposInformeFases;
    private final FacadeConfig config;

    static {
        String ruta = "json/informe/";
        String extension = ".json";
        ObjectMapper mapper = new ObjectMapper();
        InputStream streamCamposInforme = ELearningQAFacade.class.getClassLoader()
                .getResourceAsStream(ruta + "CamposInformeFases" + extension);
        try {
            camposInformeFases = mapper.readValue(streamCamposInforme, String[].class);
        } catch (Exception e) {
            LOGGER.error("exception", e);
        }
    }

    public ELearningQAFacade(String configFile, String host) {
        config = new FacadeConfig(configFile);
        config.setHost(host);
    }

    public String conectarse(String username, String password) {
        return WebServiceClient.login(username, password, config.getHost());
    }

    public String generarListaCursos(String token) {
        List<Course> listaCursos = getListaCursos(token);
        StringBuilder listaEnTabla = new StringBuilder("<table>");
        for (Course curso : listaCursos) {
            listaEnTabla.append("<tr><td><a target=\"_blank\" href=\"./informe?courseid=").append(curso.getId())
                    .append("\">").append(curso.getFullname())
                    .append(" (" + curso.getCoursecategory() + ")").append("</a></td></tr>");
        }
        listaEnTabla.append("</table>");
        return listaEnTabla.toString();
    }

    public List<Course> getListaCursos(String token) {
        return WebServiceClient.obtenerCursos(token, config.getHost());
    }

    public String obtenerNombreCompleto(String token, String username) {
        return WebServiceClient.obtenerNombreCompleto(token, username, config.getHost());
    }

    public int[] realizarComprobaciones(String token, long courseid, AlertLog registro) {
        Course curso = getCursoPorId(token, courseid);
        List<User> listaUsuarios = WebServiceClient.obtenerUsuarios(token, courseid, config.getHost());
        double siteVersion = WebServiceClient.getMoodleSiteVersion(config.getHost(), token);
        QuizList quizzes = WebServiceClient.getQuizzesByCourse(token, curso.getId(), listaUsuarios, siteVersion,
                config.getHost());
        StatusList listaEstados = WebServiceClient.obtenerListaEstados(token, courseid, listaUsuarios,
                config.getHost());
        List<es.ubu.lsi.model.Module> listaModulos = WebServiceClient.obtenerListaModulos(token, courseid,
                config.getHost());
        List<Group> listaGrupos = WebServiceClient.obtenerListaGrupos(token, courseid, config.getHost(), registro);
        List<Assignment> listaTareas = WebServiceClient.obtenerListaTareas(token, courseid, config.getHost());
        List<Table> listaCalificadores = WebServiceClient.obtenerCalificadores(token, courseid, config.getHost());
        List<Resource> listaRecursos = WebServiceClient.obtenerRecursos(token, courseid, config.getHost());
        List<CourseModule> listaModulosTareas = WebServiceClient.obtenerModulosTareas(token, listaTareas,
                config.getHost());
        List<Post> listaPosts = WebServiceClient.obtenerListaPosts(token, courseid, config.getHost());
        Map<Integer, Long> mapaFechasLimite = WebServiceClient.generarMapaFechasLimite(listaTareas);
        List<Assignment> tareasConNotas = WebServiceClient.obtenerTareasConNotas(token, mapaFechasLimite,
                config.getHost(), listaTareas);
        List<ResponseAnalysis> listaAnalisis = WebServiceClient.obtenerAnalisis(token, courseid, config.getHost());
        List<Survey> listaSurveys = WebServiceClient.obtenerSurveys(token, courseid, config.getHost());
        List<es.ubu.lsi.model.Module> modulosMalFechados = WebServiceClient.obtenerModulosMalFechados(curso,
                listaModulos);
        List<Resource> recursosDesfasados = WebServiceClient.obtenerRecursosDesfasados(curso, listaRecursos);
        int[] puntosComprobaciones = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        if (isestaProgresoActivado(listaEstados, registro)) {
            puntosComprobaciones[0]++;
        }
        if (isHayVariedadFormatos(listaModulos, registro)) {
            puntosComprobaciones[1]++;
        }
        if (isTieneGrupos(listaGrupos, registro)) {
            puntosComprobaciones[2]++;
        }
        if (isHayTareasGrupales(listaTareas, registro)) {
            puntosComprobaciones[3]++;
        }
        if (isSonVisiblesCondiciones(curso, registro)) {
            puntosComprobaciones[4]++;
        }
        if (isEsNotaMaxConsistente(listaCalificadores, registro)) {
            puntosComprobaciones[5]++;
        }
        if (courseHasDatesAndSummaryDefinde(curso, registro)) {
            puntosComprobaciones[6]++;
        }
        if (isRandomGuessScoreInQuizzesCorrect(quizzes, registro)) {
            puntosComprobaciones[7]++;
        }
        if (isEstanActualizadosRecursos(recursosDesfasados, registro)) {
            puntosComprobaciones[8]++;
        }
        if (isSonFechasCorrectas(modulosMalFechados, registro)) {
            puntosComprobaciones[9]++;
        }
        if (isMuestraCriterios(listaModulosTareas, registro)) {
            puntosComprobaciones[10]++;
        }
        if (isAnidamientoCalificadorAceptable(listaCalificadores, registro)) {
            puntosComprobaciones[11]++;
        }
        if (isAlumnosEnGrupos(listaUsuarios, registro, curso.getId())) {
            puntosComprobaciones[12]++;
        }
        if (isRespondeATiempo(listaUsuarios, listaPosts, registro)) {
            puntosComprobaciones[13]++;
        }
        if (isHayRetroalimentacion(listaCalificadores, registro)) {
            puntosComprobaciones[14]++;
        }
        if (isEstaCorregidoATiempo(tareasConNotas, listaUsuarios, registro)) {
            puntosComprobaciones[15]++;
        }
        if (isCalificadorMuestraPonderacion(listaCalificadores, registro)) {
            puntosComprobaciones[16]++;
        }
        if (isCourseFacilityIndexCorrect(quizzes, registro)) {
            puntosComprobaciones[17]++;
        }
        if (isCourseQuizzesEngagementCorrect(quizzes, registro)) {
            puntosComprobaciones[18]++;
        }
        if (isDiscriminationIndexInQuizzesCorrect(quizzes, registro)) {
            puntosComprobaciones[19]++;
        }
        if (isRespondenFeedbacks(listaAnalisis, listaUsuarios, registro)) {
            puntosComprobaciones[20]++;
        }
        if (isUsaSurveys(listaSurveys, registro)) {
            puntosComprobaciones[21]++;
        }
        return puntosComprobaciones;
    }

    public String generarInformeFases(int[] puntos, int nroCursos) {
        int contadorDiseno = puntos[0] + puntos[1] + puntos[2] + puntos[3] + puntos[4] + puntos[5] + puntos[6]
                + puntos[7];
        int contadorImplementacion = puntos[8] + puntos[9] + puntos[10] + puntos[11] + puntos[12];
        int contadorRealizacion = puntos[13] + puntos[14] + puntos[15] + puntos[16] + puntos[17] + puntos[18]
                + puntos[19];
        int contadorEvaluacion = puntos[20] + puntos[21];
        int contadorTotal = contadorDiseno + contadorImplementacion + contadorRealizacion + contadorEvaluacion;
        return camposInformeFases[0] + generarCampoRelativo((float) contadorTotal / nroCursos, CHECKS_TOTAL) +
                camposInformeFases[1] + generarCampoRelativo((float) contadorDiseno / nroCursos, CHECKS_DISENO) +
                generarFilas(new int[] { 2, 0 }, CHECKS_DISENO, puntos, nroCursos) +
                camposInformeFases[10]
                + generarCampoRelativo((float) contadorImplementacion / nroCursos, CHECKS_IMPLEMENTACION) +
                generarFilas(new int[] { 11, 8 }, CHECKS_IMPLEMENTACION, puntos, nroCursos) +
                camposInformeFases[16]
                + generarCampoRelativo((float) contadorRealizacion / nroCursos, CHECKS_REALIZACION) +
                generarFilas(new int[] { 17, 13 }, CHECKS_REALIZACION, puntos, nroCursos) +
                camposInformeFases[24] + generarCampoRelativo((float) contadorEvaluacion / nroCursos, CHECKS_EVALUACION)
                +
                generarFilas(new int[] { 25, 20 }, CHECKS_EVALUACION, puntos, nroCursos) + camposInformeFases[27];
    }

    private String generarFilas(int[] posiciones, int cantidad, int[] puntos, int nroCursos) {
        StringBuilder filas = new StringBuilder();
        if (nroCursos == 1) {
            for (int i = 0; i < cantidad; i++) {
                camposInformeFases[posiciones[0] + i] = addTooltipTitle(camposInformeFases[posiciones[0] + i],
                        posiciones[0] + i);
                filas.append(camposInformeFases[posiciones[0] + i])
                        .append(generarCampoAbsoluto(puntos[posiciones[1] + i]));
            }
        } else {
            for (int i = 0; i < cantidad; i++) {
                camposInformeFases[posiciones[0] + i] = addTooltipTitle(camposInformeFases[posiciones[0] + i],
                        posiciones[0] + i);
                filas.append(camposInformeFases[posiciones[0] + i])
                        .append(generarCampoRelativo(puntos[posiciones[1] + i], nroCursos));
            }
        }
        return filas.toString();
    }

    public boolean isSonVisiblesCondiciones(Course curso, AlertLog registro) {
        return WebServiceClient.sonVisiblesCondiciones(curso, registro);
    }

    public boolean isTieneGrupos(List<Group> listaGrupos, AlertLog registro) {
        return WebServiceClient.tieneGrupos(listaGrupos, registro);
    }

    public Course getCursoPorId(String token, long courseid) {
        return WebServiceClient.obtenerCursoPorId(token, courseid, config.getHost());
    }

    public boolean isestaProgresoActivado(StatusList listaEstados, AlertLog registro) {
        return WebServiceClient.estaProgresoActivado(listaEstados, registro);
    }

    public boolean isUsaSurveys(List<Survey> listaEncuestas, AlertLog registro) {
        return WebServiceClient.usaSurveys(listaEncuestas, registro);
    }

    public boolean isAlumnosEnGrupos(List<User> listaUsuarios, AlertLog registro, long courseId) {
        return WebServiceClient.alumnosEnGrupos(listaUsuarios, registro, courseId, config);
    }

    public boolean isEstaCorregidoATiempo(List<Assignment> tareasConNotas, List<User> listaUsuarios,
            AlertLog registro) {
        return WebServiceClient.estaCorregidoATiempo(tareasConNotas, listaUsuarios, registro, config);
    }

    public boolean isRespondeATiempo(List<User> listaUsuarios, List<Post> listaPostsCompleta, AlertLog registro) {
        return WebServiceClient.respondeATiempo(listaUsuarios, listaPostsCompleta, registro, config);
    }

    public boolean isAnidamientoCalificadorAceptable(List<Table> listaCalificadores, AlertLog registro) {
        return WebServiceClient.anidamientoCalificadorAceptable(listaCalificadores, registro, config);
    }

    public boolean isCalificadorMuestraPonderacion(List<Table> listaCalificadores, AlertLog registro) {
        return WebServiceClient.calificadorMuestraPonderacion(listaCalificadores, registro);
    }

    public boolean isHayRetroalimentacion(List<Table> listaCalificadores, AlertLog registro) {
        return WebServiceClient.hayRetroalimentacion(listaCalificadores, registro, config);
    }

    public boolean isEsNotaMaxConsistente(List<Table> listaCalificadores, AlertLog registro) {
        return WebServiceClient.esNotaMaxConsistente(listaCalificadores, registro);
    }

    public boolean isEstanActualizadosRecursos(List<Resource> listaRecursosDesfasados, AlertLog registro) {
        return WebServiceClient.estanActualizadosRecursos(listaRecursosDesfasados, registro, config);
    }

    public boolean isSonFechasCorrectas(List<es.ubu.lsi.model.Module> listaModulosMalFechados, AlertLog registro) {
        return WebServiceClient.sonFechasCorrectas(listaModulosMalFechados, registro);
    }

    public boolean isRespondenFeedbacks(List<ResponseAnalysis> listaAnalisis, List<User> listaUsuarios,
            AlertLog registro) {
        return WebServiceClient.respondenFeedbacks(listaAnalisis, listaUsuarios, registro, config);
    }

    public boolean isHayTareasGrupales(List<Assignment> listaTareas, AlertLog registro) {
        return WebServiceClient.hayTareasGrupales(listaTareas, registro);
    }

    public boolean isMuestraCriterios(List<CourseModule> listaModulosTareas, AlertLog registro) {
        return WebServiceClient.muestraCriterios(listaModulosTareas, registro);
    }

    public boolean isHayVariedadFormatos(List<es.ubu.lsi.model.Module> listamodulos, AlertLog registro) {
        return WebServiceClient.hayVariedadFormatos(listamodulos, registro, config);
    }

    public boolean isCourseFacilityIndexCorrect(QuizList quizzes, AlertLog registro) {
        return WebServiceClient.isCourseFacilityIndexCorrect(quizzes, registro, config);
    }

    public boolean isCourseQuizzesEngagementCorrect(QuizList quizzes, AlertLog registro) {
        return WebServiceClient.isCourseQuizzesEngagementCorrect(quizzes, registro, config);
    }

    public boolean isRandomGuessScoreInQuizzesCorrect(QuizList quizzes,
            AlertLog registro) {
        return WebServiceClient.isRandomGuessScoreInQuizzesCorrect(quizzes, registro, config);
    }

    public boolean isDiscriminationIndexInQuizzesCorrect(QuizList quizzes,
            AlertLog registro) {
        return WebServiceClient.isDiscriminationIndexInQuizzesCorrect(quizzes, registro, config);
    }

    public boolean courseHasDatesAndSummaryDefinde(Course course, AlertLog registro) {
        return WebServiceClient.courseHasDatesAndSummaryDefinde(course, registro);
    }

    public float porcentajeFraccion(float numerador, float denominador) {
        return numerador / denominador * 100;
    }

    public String generarCampoAbsoluto(int puntos) {
        if (puntos == 0) {
            return "<td class=\"tg-pred\"><img src=\"Cross.png\" width=\"16\" height=\"16\" alt=\"No\"></td>";
        } else {
            return "<td class=\"tg-pgre\"><img src=\"Check.png\" width=\"16\" height=\"16\" alt=\"Sí." +
                    "\"></td>";
        }
    }

    public String generarCampoRelativo(float numerador, float denominador) {
        float resultado = numerador / denominador;
        String campoAMedias = "<meter value=\"" + numerador + "\" min=\"0\" max=\"" + denominador + "\"></meter>" +
                String.format("%.1f", porcentajeFraccion(numerador, denominador)) + "%" + "</td>";
        if (resultado < 0.2) {
            return "<td class=\"tg-pred\">" + campoAMedias;
        }
        if (resultado < 0.4) {
            return "<td class=\"tg-oran\">" + campoAMedias;
        }
        if (resultado < 0.6) {
            return "<td class=\"tg-yell\">" + campoAMedias;
        }
        if (resultado < 0.8) {
            return "<td class=\"tg-char\">" + campoAMedias;
        } else {
            return "<td class=\"tg-pgre\">" + campoAMedias;
        }
    }

    public float[] calcularPorcentajesMatriz(int[] puntos, int numeroCursos) {
        int[][] matrizPuntos = new int[][] {
                { 3, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 3, 1, 1, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 3, 1, 1 },
                { 3, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 3, 1, 1, 3, 1, 1, 0, 0, 0 },
                { 1, 3, 1, 1, 3, 1, 0, 0, 0 },
                { 3, 1, 1, 3, 1, 1, 0, 0, 0 },
                { 3, 1, 1, 0, 0, 0, 3, 1, 1 },
                { 0, 0, 0, 1, 0, 3, 1, 0, 3 },
                { 1, 3, 1, 1, 3, 1, 0, 0, 0 },
                { 1, 3, 1, 1, 3, 1, 0, 0, 0 },
                { 1, 3, 1, 1, 3, 1, 0, 0, 0 },
                { 1, 3, 1, 1, 3, 1, 0, 0, 0 },
                { 1, 3, 1, 1, 3, 1, 1, 3, 1 },
                { 1, 3, 1, 1, 3, 1, 1, 3, 1 },
                { 1, 3, 1, 1, 3, 1, 1, 3, 1 },
                { 1, 1, 3, 1, 1, 3, 1, 1, 3 },
                { 1, 1, 3, 1, 1, 3, 1, 1, 3 }
        };
        float[] porcentajes = new float[9];
        int[] puntuacionesMax = new int[] { 42 * numeroCursos, 37 * numeroCursos, 23 * numeroCursos, 20 * numeroCursos,
                29 * numeroCursos, 20 * numeroCursos, 12 * numeroCursos, 13 * numeroCursos, 14 * numeroCursos };
        for (int i = 0; i < matrizPuntos.length; i++) {
            for (int j = 0; j < porcentajes.length; j++) {
                porcentajes[j] += (float) (matrizPuntos[i][j] * puntos[i]) / puntuacionesMax[j];
            }
        }
        return porcentajes;
    }

    public String generarMatrizRolPerspectiva(float[] porcentajes) {
        return "<table class=\"tg\"><tr><td class=\"tg-plgr\"></td><td class=\"tg-plgr\">Diseñador</td>" +
                "<td class=\"tg-plgr\">Facilitador</td><td class=\"tg-plgr\">Proveedor</td>" +
                "</tr><tr><td class=\"tg-plgr\">Pedagógica</td>" + generarCampoRelativo(porcentajes[0], 1) +
                generarCampoRelativo(porcentajes[1], 1) + generarCampoRelativo(porcentajes[2], 1) +
                "</tr><tr><td class=\"tg-plgr\">Tecnológica</td>" + generarCampoRelativo(porcentajes[3], 1) +
                generarCampoRelativo(porcentajes[4], 1) + generarCampoRelativo(porcentajes[5], 1) +
                "</tr><tr><td class=\"tg-plgr\">Estratégica</td>" + generarCampoRelativo(porcentajes[6], 1) +
                generarCampoRelativo(porcentajes[7], 1) + generarCampoRelativo(porcentajes[8], 1) +
                "</tr></table>";
    }

    private String addTooltipTitle(String tableRow, int tableRowIndex) {
        String regexTooltip = "titleHere";
        if (tableRowIndex == 9) {
            tableRow = tableRow.replace(
                    regexTooltip,
                    "title=\" Se comprueba que la suma de las calificaiones" +
                            " aleatorias de las preguntas de los cuestionarios se encuentren por debajo de: "
                            + config.getMaxRandomScoreInQuizz() * 100 + "%\"");
        }
        if (tableRowIndex == 21) {
            tableRow = tableRow.replace(
                    regexTooltip,
                    "title=\"Se comprueba que las preguntas de los cuestionarios tienen un índice de facilidad "
                            +
                            "comprendido en el intervalo ["
                            + (int) (config.getFacilityIndexMin() * 100)
                            + "% - "
                            + (int) (config.getFacilityIndexMax() * 100) + "%]\"");
        }
        if (tableRowIndex == 22) {
            tableRow = tableRow.replace(
                    regexTooltip,
                    "title=\"Se comprueba que los cuestionarios tienen una participación superior al "
                            + (int) (config.getMinQuizEngagementPercentage() * 100) + "%\"");
        }
        if (tableRowIndex == 23) {
            tableRow = tableRow.replace(
                    regexTooltip,
                    "title=\"Se comprueba que el índice de discriminación de " +
                            "las preguntas de los cuestionarios sea superior al "
                            + (int) (config.getMinQuizDiscriminationIndex() * 100) + "%\"");
        }

        return tableRow;
    }
}
