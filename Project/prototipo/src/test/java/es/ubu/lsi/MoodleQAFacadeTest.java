package es.ubu.lsi;

import es.ubu.lsi.model.Course;
import org.junit.jupiter.api.BeforeEach;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoodleQAFacadeTest {

    MoodleQAFacade fachada;
    String token;
    int idCurso1=66;
    int idCurso2=56;
    int idCurso3=59;
    int idCurso4=27;
    int idCurso5=41;
    int idCurso6=68;


    @BeforeEach
    void creaFachada(){
        fachada=new MoodleQAFacade("teacher","moodle");
        token=fachada.conectarse("teacher","moodle");
    }

    @org.junit.jupiter.api.Test
    void conectarseTest() {
        assertFalse(fachada.conectarse("teacher","moodle").contains("Invalid"));
        assertTrue(fachada.conectarse("teacher","patata").contains("Invalid"));
    }


    @org.junit.jupiter.api.Test
    void getListaCursosTest() {
        List<Course> listaCursos = fachada.getListaCursos(token);
        List<Integer> listaIds = new ArrayList<Integer>();
        for (Course curso:listaCursos) {
            listaIds.add(curso.getId());
        }
        assertTrue(listaIds.contains(idCurso1));
        assertTrue(listaIds.contains(idCurso2));
        assertTrue(listaIds.contains(idCurso3));
        assertTrue(listaIds.contains(idCurso4));
        assertTrue(listaIds.contains(idCurso5));
        assertTrue(listaIds.contains(idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isSonVisiblesCondicionesTest() {
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso1));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso2));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso3));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso4));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso5));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isTieneGruposTest() {
        assertTrue(fachada.isTieneGrupos(token, idCurso1));
        assertFalse(fachada.isTieneGrupos(token, idCurso2));
        assertTrue(fachada.isTieneGrupos(token, idCurso3));
        assertFalse(fachada.isTieneGrupos(token, idCurso4));
        assertTrue(fachada.isTieneGrupos(token, idCurso5));
        assertTrue(fachada.isTieneGrupos(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void getCursoPorIdTest() {
        assertEquals("Digital Literacy ",fachada.getCursoPorId(token, idCurso1).getFullname());
        assertEquals("History: Russia in Revolution",fachada.getCursoPorId(token, idCurso2).getFullname());
        assertEquals("Celebrating Cultures",fachada.getCursoPorId(token, idCurso3).getFullname());
        assertEquals("Parents and Citizens Council",fachada.getCursoPorId(token, idCurso4).getFullname());
        assertEquals("Staffroom",fachada.getCursoPorId(token, idCurso5).getFullname());
        assertEquals("Votes for Women!",fachada.getCursoPorId(token, idCurso6).getFullname());
    }

    @org.junit.jupiter.api.Test
    void porcentajeFraccionTest() {
        assertEquals(20,fachada.porcentajeFraccion(1,5 ));
        assertEquals(34,fachada.porcentajeFraccion(17, 50));
        assertEquals(55,fachada.porcentajeFraccion(11, 20));
        assertEquals(100,fachada.porcentajeFraccion(9, 9));
    }

    @org.junit.jupiter.api.Test
    void isEstaProgresoActivadoTest() {
        assertTrue(fachada.isestaProgresoActivado(token, idCurso1));
        assertTrue(fachada.isestaProgresoActivado(token, idCurso2));
        assertTrue(fachada.isestaProgresoActivado(token, idCurso3));
        assertFalse(fachada.isestaProgresoActivado(token, idCurso4));
        assertTrue(fachada.isestaProgresoActivado(token, idCurso5));
        assertTrue(fachada.isestaProgresoActivado(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isEstaCorregidoATiempoTest() {
        assertFalse(fachada.isEstaCorregidoATiempo(token, idCurso1));
        assertFalse(fachada.isEstaCorregidoATiempo(token, idCurso2));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso3));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso4));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso5));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isRespondeATiempoTest() {
        assertTrue(fachada.isRespondeATiempo(token, idCurso1));
        assertTrue(fachada.isRespondeATiempo(token, idCurso2));
        assertTrue(fachada.isRespondeATiempo(token, idCurso3));
        assertTrue(fachada.isRespondeATiempo(token, idCurso4));
        assertFalse(fachada.isRespondeATiempo(token, idCurso5));
        assertTrue(fachada.isRespondeATiempo(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isUsaSurveysTest() {
        assertTrue(fachada.isUsaSurveys(token, idCurso1));
        assertFalse(fachada.isUsaSurveys(token, idCurso2));
        assertFalse(fachada.isUsaSurveys(token, idCurso3));
        assertFalse(fachada.isUsaSurveys(token, idCurso4));
        assertFalse(fachada.isUsaSurveys(token, idCurso5));
        assertTrue(fachada.isUsaSurveys(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isAlumnosEnGruposTest() {
        assertFalse(fachada.isAlumnosEnGrupos(token, idCurso1));
        assertFalse(fachada.isAlumnosEnGrupos(token, idCurso2));
        assertFalse(fachada.isAlumnosEnGrupos(token, idCurso3));
        assertFalse(fachada.isAlumnosEnGrupos(token, idCurso4));
        assertFalse(fachada.isAlumnosEnGrupos(token, idCurso5));
        assertTrue(fachada.isAlumnosEnGrupos(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isAnidamientoCalificadorAceptableTest() {
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso1));
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso2));
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso3));
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso4));
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso5));
        assertTrue(fachada.isAnidamientoCalificadorAceptable(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isCalificadorMuestraPonderacionTest() {
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso1));
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso2));
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso3));
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso4));
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso5));
        assertTrue(fachada.isCalificadorMuestraPonderacion(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isHayRetroalimentacionTest() {
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso1));
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso2));
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso3));
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso4));
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso5));
        assertFalse(fachada.isHayRetroalimentacion(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isEsNotaMaxConsistenteTest() {
        assertFalse(fachada.isEsNotaMaxConsistente(token, idCurso1));
        assertFalse(fachada.isEsNotaMaxConsistente(token, idCurso2));
        assertFalse(fachada.isEsNotaMaxConsistente(token, idCurso3));
        assertTrue(fachada.isEsNotaMaxConsistente(token, idCurso4));
        assertFalse(fachada.isEsNotaMaxConsistente(token, idCurso5));
        assertFalse(fachada.isEsNotaMaxConsistente(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isEstanActualizadosRecursosTest() {
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso1));
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso2));
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso3));
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso4));
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso5));
        assertTrue(fachada.isEstanActualizadosRecursos(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isSonFechasCorrectasTest() {
        assertTrue(fachada.isSonFechasCorrectas(token, idCurso1));
        assertTrue(fachada.isSonFechasCorrectas(token, idCurso2));
        assertTrue(fachada.isSonFechasCorrectas(token, idCurso3));
        assertTrue(fachada.isSonFechasCorrectas(token, idCurso4));
        assertTrue(fachada.isSonFechasCorrectas(token, idCurso5));
        assertFalse(fachada.isSonFechasCorrectas(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isRespondenFeedbacksTest() {
        assertFalse(fachada.isRespondenFeedbacks(token, idCurso1));
        assertTrue(fachada.isRespondenFeedbacks(token, idCurso2));
        assertTrue(fachada.isRespondenFeedbacks(token, idCurso3));
        assertTrue(fachada.isRespondenFeedbacks(token, idCurso4));
        assertTrue(fachada.isRespondenFeedbacks(token, idCurso5));
        assertTrue(fachada.isRespondenFeedbacks(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isHayTareasGrupalesTest() {
        assertFalse(fachada.isHayTareasGrupales(token, idCurso1));
        assertFalse(fachada.isHayTareasGrupales(token, idCurso2));
        assertFalse(fachada.isHayTareasGrupales(token, idCurso3));
        assertFalse(fachada.isHayTareasGrupales(token, idCurso4));
        assertFalse(fachada.isHayTareasGrupales(token, idCurso5));
        assertTrue(fachada.isHayTareasGrupales(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isMuestraCriteriosTest() {
        assertFalse(fachada.isMuestraCriterios(token, idCurso1));
        assertFalse(fachada.isMuestraCriterios(token, idCurso2));
        assertFalse(fachada.isMuestraCriterios(token, idCurso3));
        assertFalse(fachada.isMuestraCriterios(token, idCurso4));
        assertFalse(fachada.isMuestraCriterios(token, idCurso5));
        assertFalse(fachada.isMuestraCriterios(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void isHayVariedadFormatosTest() {
        assertTrue(fachada.isHayVariedadFormatos(token, idCurso1));
        assertTrue(fachada.isHayVariedadFormatos(token, idCurso2));
        assertFalse(fachada.isHayVariedadFormatos(token, idCurso3));
        assertFalse(fachada.isHayVariedadFormatos(token, idCurso4));
        assertFalse(fachada.isHayVariedadFormatos(token, idCurso5));
        assertTrue(fachada.isHayVariedadFormatos(token, idCurso6));
    }

    @org.junit.jupiter.api.Test
    void generarCampoAbsolutoTest() {
        assertEquals(fachada.generarCampoAbsoluto(true),"<td class=\"tg-pgre\">Sí</td>");
        assertEquals(fachada.generarCampoAbsoluto(false),"<td class=\"tg-pred\">No</td>");
    }

    @org.junit.jupiter.api.Test
    void generarCampoRelativoTest() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        char sep=symbols.getDecimalSeparator();
        assertEquals(fachada.generarCampoRelativo(-0.1f,100),"<td class=\"tg-pred\"><meter value=\"-0.1\" min=\"0\" max=\"100.0\"></meter>-0"+sep+"1%</td>");
        assertEquals(fachada.generarCampoRelativo(0,100),"<td class=\"tg-pred\"><meter value=\"0.0\" min=\"0\" max=\"100.0\"></meter>0"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(19.9f,100),"<td class=\"tg-pred\"><meter value=\"19.9\" min=\"0\" max=\"100.0\"></meter>19"+sep+"9%</td>");
        assertEquals(fachada.generarCampoRelativo(20,100),"<td class=\"tg-oran\"><meter value=\"20.0\" min=\"0\" max=\"100.0\"></meter>20"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(39.9f,100),"<td class=\"tg-oran\"><meter value=\"39.9\" min=\"0\" max=\"100.0\"></meter>39"+sep+"9%</td>");
        assertEquals(fachada.generarCampoRelativo(40.0f,100),"<td class=\"tg-yell\"><meter value=\"40.0\" min=\"0\" max=\"100.0\"></meter>40"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(59.9f,100),"<td class=\"tg-yell\"><meter value=\"59.9\" min=\"0\" max=\"100.0\"></meter>59"+sep+"9%</td>");
        assertEquals(fachada.generarCampoRelativo(60,100),"<td class=\"tg-char\"><meter value=\"60.0\" min=\"0\" max=\"100.0\"></meter>60"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(79.9f,100),"<td class=\"tg-char\"><meter value=\"79.9\" min=\"0\" max=\"100.0\"></meter>79"+sep+"9%</td>");
        assertEquals(fachada.generarCampoRelativo(80,100),"<td class=\"tg-pgre\"><meter value=\"80.0\" min=\"0\" max=\"100.0\"></meter>80"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(100,100),"<td class=\"tg-pgre\"><meter value=\"100.0\" min=\"0\" max=\"100.0\"></meter>100"+sep+"0%</td>");
        assertEquals(fachada.generarCampoRelativo(100.1f,100),"<td class=\"tg-pgre\"><meter value=\"100.1\" min=\"0\" max=\"100.0\"></meter>100"+sep+"1%</td>");
    }
}