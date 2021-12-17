package es.ubu.lsi;

import es.ubu.lsi.model.Curso;

import java.util.List;

public class Fachada {
    public Fachada(String username, String password) {

    }

    public String conectarse(String username, String password){
        return WebServiceClient.login(username, password);
    }

    public String generarListaCursos(String token){
        List<Curso> listaCursos= getListaCursos(token);
        String listaEnTabla="<table border=\"1\"><tr><th>Lista de cursos</th></tr>";
        for (Curso curso: listaCursos) {
            listaEnTabla+="<tr><td><a href=\"../informe?courseid="+String.valueOf(curso.getId())+"\">"+curso.getFullname()+"<a></td></tr>";
        }
        listaEnTabla+="</table>";
        return listaEnTabla;
    }

    public List<Curso> getListaCursos(String token) {
        return WebServiceClient.obtenerCursos(token);
    }

    public String generarInformeEspecifico(String token, int courseid){
        Curso curso= getCursoPorId(token, courseid);
        boolean tienegrupos= isTieneGrupos(token, courseid);
        boolean sonVisiblesCondiciones= isSonVisiblesCondiciones(token, courseid);
        int contadorDiseno=0;
        int checksDiseno=2;
        int contadorTotal=0;
        int checksTotal=0;
        if(tienegrupos){contadorDiseno++;}
        if(sonVisiblesCondiciones){contadorDiseno++;}
        contadorTotal=contadorDiseno;
        checksTotal=checksDiseno;//Aqui se suman todas las categorias
        return "<h2>Informe: "+curso.getFullname()+"</h2>"+
                "<table border=\"1\">"+
                "<tr><td><p style=\"text-align: right\">Puntuacion general:</p></td><td>"+
                    "<meter value=\""+String.valueOf(contadorTotal)+"\" min=\"0\" max=\""+String.valueOf(checksTotal)+"\"></meter>"+
                        String.valueOf(porcentajeFraccion(contadorTotal,checksTotal))+"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">Diseno:</p></td><td>"+
                    "<meter value=\""+String.valueOf(contadorDiseno)+"\" min=\"0\" max=\""+String.valueOf(checksDiseno)+"\"></meter>"+
                        String.valueOf(porcentajeFraccion(contadorDiseno,checksDiseno))+"%"+"</td>"+
                "<tr><td><p style=\"text-align: right\">El curso tiene grupos</p></td><td>"+
                    (tienegrupos?"Si":"No")+"</td>"+
                "<tr><td><p style=\"text-align: right\">Los estudiantes pueden ver las condiciones necesarias para completar una actividad</p></td><td>"+
                    (sonVisiblesCondiciones?"Si":"No")+"</td>"+
                "</table>";
    }

    public boolean isSonVisiblesCondiciones(String token, int courseid) {
        return WebServiceClient.sonVisiblesCondiciones(token, courseid);
    }

    public boolean isTieneGrupos(String token, int courseid) {
        return WebServiceClient.tieneGrupos(token, courseid);
    }

    public Curso getCursoPorId(String token, int courseid) {
        return WebServiceClient.obtenerCursoPorId(token, courseid);
    }

    public float porcentajeFraccion(float numerador, float denominador){
        return numerador/denominador*100;
    };

    public String generarInformeGlobal(String token){
        List<Curso> listaCursos= getListaCursos(token);
        int counter=0;
        for (Curso curso: listaCursos) {
            if(isTieneGrupos(token, curso.getId())){
                counter++;
            }
        }
        return "Cursos con grupos:"+String.valueOf(counter)+"/"+String.valueOf(listaCursos.size())+
                "<meter value=\""+String.valueOf(counter)+"\" min=\"0\" max=\""+String.valueOf(listaCursos.size())+"\"></meter>";
    }
}
