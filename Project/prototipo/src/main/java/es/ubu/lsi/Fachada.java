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
        List<Curso> listaCursos=WebServiceClient.obtenerCursos(token);
        String listaEnTabla="<table border=\"1\">";
        for (Curso curso: listaCursos) {
            listaEnTabla+="<tr><td><a href=\"../informe?courseid="+String.valueOf(curso.getId())+"\">"+curso.getFullname()+"<a></td></tr>";
        }
        listaEnTabla+="</table>";
        return listaEnTabla;
    }

    public String generarInformeEspecifico(String token, int courseid){
        return "El curso "+Integer.toString(courseid)+(WebServiceClient.tieneGrupos(token,courseid)?" si":" no")+" tiene grupos";
    }

    public String generarInformeGlobal(String token){
        List<Curso> listaCursos=WebServiceClient.obtenerCursos(token);
        int counter=0;
        for (Curso curso: listaCursos) {
            if(WebServiceClient.tieneGrupos(token,curso.getId())){
                counter++;
            }
        }
        return "Cursos con grupos:"+String.valueOf(counter)+"/"+String.valueOf(listaCursos.size())+
                "<meter value=\""+String.valueOf(counter)+"\" min=\"0\" max=\""+String.valueOf(listaCursos.size())+"\"></meter>";
    }
}
