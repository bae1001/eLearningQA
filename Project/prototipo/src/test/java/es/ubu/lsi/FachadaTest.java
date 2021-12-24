package es.ubu.lsi;

import es.ubu.lsi.model.Course;
import org.junit.jupiter.api.BeforeEach;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FachadaTest {

    Fachada fachada;
    String token;
    int idCurso1=66;
    int idCurso2=56;
    int idCurso3=59;
    int idCurso4=27;
    int idCurso5=41;


    @BeforeEach
    void creaFachada(){
        fachada=new Fachada("teacher","moodle");
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
        System.out.println(listaCursos);
        List<Integer> listaIds = new ArrayList<Integer>();
        for (Course curso:listaCursos) {
            listaIds.add(curso.getId());
        }
        assertTrue(listaIds.contains(idCurso1));
        assertTrue(listaIds.contains(idCurso2));
        assertTrue(listaIds.contains(idCurso3));
        assertTrue(listaIds.contains(idCurso4));
        assertTrue(listaIds.contains(idCurso5));
    }

    @org.junit.jupiter.api.Test
    void isSonVisiblesCondicionesTest() {
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso1));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso2));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso3));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso4));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso5));
    }

    @org.junit.jupiter.api.Test
    void isTieneGruposTest() {
        assertTrue(fachada.isTieneGrupos(token, idCurso1));
        assertFalse(fachada.isTieneGrupos(token, idCurso2));
        assertTrue(fachada.isTieneGrupos(token, idCurso3));
        assertFalse(fachada.isTieneGrupos(token, idCurso4));
        assertTrue(fachada.isTieneGrupos(token, idCurso5));
    }

    @org.junit.jupiter.api.Test
    void getCursoPorIdTest() {
        assertEquals("Digital Literacy ",fachada.getCursoPorId(token, idCurso1).getFullname());
        assertEquals("History: Russia in Revolution",fachada.getCursoPorId(token, idCurso2).getFullname());
        assertEquals("Celebrating Cultures",fachada.getCursoPorId(token, idCurso3).getFullname());
        assertEquals("Parents and Citizens Council",fachada.getCursoPorId(token, idCurso4).getFullname());
        assertEquals("Staffroom",fachada.getCursoPorId(token, idCurso5).getFullname());
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
    }

    @org.junit.jupiter.api.Test
    void isEstaCorregidoATiempoTest() {
        assertFalse(fachada.isEstaCorregidoATiempo(token, idCurso1));
        assertFalse(fachada.isEstaCorregidoATiempo(token, idCurso2));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso3));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso4));
        assertTrue(fachada.isEstaCorregidoATiempo(token, idCurso5));
    }

    @org.junit.jupiter.api.Test
    void isRespondeATiempoTest() throws URISyntaxException {
        assertTrue(fachada.isRespondeATiempo(token, idCurso1));
        assertTrue(fachada.isRespondeATiempo(token, idCurso2));
        assertTrue(fachada.isRespondeATiempo(token, idCurso3));
        assertTrue(fachada.isRespondeATiempo(token, idCurso4));
        assertFalse(fachada.isRespondeATiempo(token, idCurso5));
    }
}