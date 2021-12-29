package es.ubu.lsi;

import es.ubu.lsi.model.Course;

import java.net.URISyntaxException;
import java.util.List;

public class Fachada {
    public Fachada(String username, String password) {

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
        int contadorDiseno=0;
        int checksDiseno=3;
        int contadorRealizacion=0;
        int checksRealizacion=2;
        int contadorTotal=0;
        int checksTotal=0;
        if(tienegrupos){contadorDiseno++;}
        if(sonVisiblesCondiciones){contadorDiseno++;}
        if(progresoActivado){contadorDiseno++;}
        if(corregidoATiempo){contadorRealizacion++;}
        if(respondeATiempo){contadorRealizacion++;}
        contadorTotal=contadorDiseno+contadorRealizacion;
        checksTotal=checksDiseno+checksRealizacion;//Aqui se suman todas las categorias
        return "<h2>Informe: "+curso.getFullname()+"</h2>"+
                "<table border=\"1\">"+
                "<tr><td><p style=\"text-align: right\">Puntuación general:</p></td><td>"+
                    "<meter value=\""+String.valueOf(contadorTotal)+"\" min=\"0\" max=\""+String.valueOf(checksTotal)+"\"></meter>"+
                        String.valueOf(porcentajeFraccion(contadorTotal,checksTotal))+"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Diseño:</p></td><td>"+
                    "<meter value=\""+String.valueOf(contadorDiseno)+"\" min=\"0\" max=\""+String.valueOf(checksDiseno)+"\"></meter>"+
                        String.valueOf(porcentajeFraccion(contadorDiseno,checksDiseno))+"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Las opciones de progreso del estudiante están activadas</p></td><td>"+
                    (progresoActivado?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El curso tiene grupos</p></td><td>"+
                    (tienegrupos?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Los estudiantes pueden ver las condiciones necesarias para completar una actividad</p></td><td>"+
                    (sonVisiblesCondiciones?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Realización:</p></td><td>"+
                    "<meter value=\""+String.valueOf(contadorRealizacion)+"\" min=\"0\" max=\""+String.valueOf(checksRealizacion)+"\"></meter>"+
                        String.valueOf(porcentajeFraccion(contadorRealizacion,checksRealizacion))+"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Las tareas están calificadas</p></td><td>"+
                    (corregidoATiempo?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">El profesor responde en los foros dentro del límite de 48 horas lectivas desde que se plantea la duda</p></td><td>"+
                    (respondeATiempo?"Si":"No")+"</td>"+
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

    public boolean isEstaCorregidoATiempo(String token, int courseid) {
        return WebServiceClient.estaCorregidoATiempo(token, courseid);
    }

    public boolean isRespondeATiempo(String token, int courseid) throws URISyntaxException {
        return WebServiceClient.respondeATiempo(token, courseid);
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
