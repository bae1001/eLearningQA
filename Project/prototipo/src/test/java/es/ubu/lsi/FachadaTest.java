package es.ubu.lsi;

import es.ubu.lsi.model.Curso;
import org.junit.jupiter.api.BeforeEach;

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


    @BeforeEach
    void creaFachada(){
        fachada=new Fachada("teacher","moodle");
        token=fachada.conectarse("teacher","moodle");
    }

    @org.junit.jupiter.api.Test
    void conectarse() {
        assertFalse(fachada.conectarse("teacher","moodle").contains("Invalid"));
        assertTrue(fachada.conectarse("teacher","patata").contains("Invalid"));
    }


    @org.junit.jupiter.api.Test
    void getListaCursos() {
        List<Curso> listaCursos = fachada.getListaCursos(token);
        System.out.println(listaCursos);
        List<Integer> listaIds = new ArrayList<Integer>();
        for (Curso curso:listaCursos) {
            listaIds.add(new Integer(curso.getId()));
        }
        assertTrue(listaIds.contains(new Integer(idCurso1)));
        assertTrue(listaIds.contains(new Integer(idCurso2)));
        assertTrue(listaIds.contains(new Integer(idCurso3)));
        assertTrue(listaIds.contains(new Integer(idCurso4)));
    }

    @org.junit.jupiter.api.Test
    void isSonVisiblesCondiciones() {
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso1));
        assertTrue(fachada.isSonVisiblesCondiciones(token, idCurso2));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso3));
        assertFalse(fachada.isSonVisiblesCondiciones(token, idCurso4));
    }

    @org.junit.jupiter.api.Test
    void isTieneGrupos() {
        assertTrue(fachada.isTieneGrupos(token, idCurso1));
        assertFalse(fachada.isTieneGrupos(token, idCurso2));
        assertTrue(fachada.isTieneGrupos(token, idCurso3));
        assertFalse(fachada.isTieneGrupos(token, idCurso4));
    }

    @org.junit.jupiter.api.Test
    void getCursoPorId() {
        assertEquals("Digital Literacy ",fachada.getCursoPorId(token, idCurso1).getFullname());
        assertEquals("History: Russia in Revolution",fachada.getCursoPorId(token, idCurso2).getFullname());
        assertEquals("Celebrating Cultures",fachada.getCursoPorId(token, idCurso3).getFullname());
        assertEquals("Parents and Citizens Council",fachada.getCursoPorId(token, idCurso4).getFullname());
    }

    @org.junit.jupiter.api.Test
    void porcentajeFraccion() {
        assertEquals(20,fachada.porcentajeFraccion(1,5 ));
        assertEquals(34,fachada.porcentajeFraccion(17, 50));
        assertEquals(55,fachada.porcentajeFraccion(11, 20));
        assertEquals(100,fachada.porcentajeFraccion(9, 9));
    }
}