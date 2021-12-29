package es.ubu.lsi;

import es.ubu.lsi.model.*;
import org.springframework.web.client.RestTemplate;



import java.util.*;

public class WebServiceClient {
    public static String login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class).split("\"", 0)[3];
    }

    public static List<User> obtenerUsuarios(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_enrol_get_enrolled_users&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid;
        return new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, User[].class)));
    }

    public static boolean esProfesor(List<User> usuarios, int userid){
        for (User usuario:usuarios) {
            if(usuario.getId()==userid){
                for (Role rol: usuario.getRoles()) {
                    if(rol.getRoleid()==3||rol.getRoleid()==4){return true;}
                }
            }
        }
        return false;
    }

    public static boolean esAlumno(List<User> usuarios, int userid){
        for (User usuario:usuarios) {
            if(usuario.getId()==userid){
                for (Role rol: usuario.getRoles()) {
                    if(rol.getRoleid()==5){return true;}
                }
            }
        }
        return false;
    }

    public static List<Course> obtenerCursos(String token){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_enrolled_courses_by_timeline_classification&moodlewsrestformat=json&wstoken="+token+"&classification=inprogress";
        return restTemplate.getForObject(url,CourseList.class).getCourses();
    }

    public static Course obtenerCursoPorId(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_courses_by_field&moodlewsrestformat=json&wstoken="+token+"&field=id&value="+courseid;
        return restTemplate.getForObject(url,CourseList.class).getCourses().get(0);
    }

    public static boolean tieneGrupos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_group_get_course_groups&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid;
        List<Group> listaGrupos= new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, Group[].class)));
        return listaGrupos.size()!=0;
    }

    public static boolean sonVisiblesCondiciones(String token, int courseid){
        Course curso=obtenerCursoPorId(token, courseid);
        if(curso.getShowcompletionconditions()!=null){
            return curso.getShowcompletionconditions();
        }
        return false;
    }

    public static boolean estaProgresoActivado(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        List<User> listaUsuarios=obtenerUsuarios(token,courseid);
        int idProfesor=listaUsuarios.get(0).getId();
        for (User usuario:listaUsuarios) {
            if(esProfesor(listaUsuarios,usuario.getId())){
                idProfesor=usuario.getId();
            }
        }
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_completion_get_activities_completion_status&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid+"&userid="+idProfesor;
        StatusList listaEstados=restTemplate.getForObject(url,StatusList.class);
        return listaEstados.isHasCompletion();
    }

    public static boolean estaCorregidoATiempo(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        List<Assignment> listaTareas = getListaTareas(token, courseid);
        Map<Integer, Integer> mapaFechasLimite= new HashMap<>();
        for (Assignment tarea:listaTareas) {
            if(tarea.getDuedate()!=0){
                mapaFechasLimite.put(tarea.getId(),tarea.getDuedate());
            }
        }
        if(mapaFechasLimite.size()==0){return true;}
        StringBuilder url2 = new StringBuilder("https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_grades&moodlewsrestformat=json&wstoken=" + token);
        int contador=0;
        for (Integer id:mapaFechasLimite.keySet()) {
            url2.append("&assignmentids[").append(contador).append("]=").append(id);
        }
        List<Assignment> tareasConNotas= restTemplate.getForObject(url2.toString(),AssignmentList.class).getAssignments();
        if (tareasConNotas!=null) {
            for (Assignment tarea : tareasConNotas) {
                List<Grade> notas = tarea.getGrades();
                for (Grade nota : notas) {
                    if(Objects.equals(nota.getGrade(), "")){nota.setGrade("-1.00000");}
                    if (nota.getTimemodified() - mapaFechasLimite.get(tarea.getId()) > 604800 ||
                            (System.currentTimeMillis() / 1000L) - mapaFechasLimite.get(tarea.getId()) > 604800 && Float.parseFloat(nota.getGrade()) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static List<Assignment> getListaTareas(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_assignments&moodlewsrestformat=json&wstoken="+ token;
        CourseList listaCursos= restTemplate.getForObject(url,CourseList.class);
        List<Assignment> listaTareas= new ArrayList<>();
        for (Course curso:listaCursos.getCourses()) {
            if(curso.getId()== courseid) {
                listaTareas.addAll(curso.getAssignments());
            }
        }
        return listaTareas;
    }

    public static boolean respondeATiempo(String token, int courseid){
        List<Forum> listaForos=getListaForos(token, courseid);
        List<Discussion> listaDebates;
        List<Discussion> listaDebatesCompleta= new ArrayList<>();
        for (Forum foro:listaForos) {
            listaDebates = getListaDebates(token, foro);
            listaDebatesCompleta.addAll(listaDebates);
        }
        List<Post> listaPostsDebate;
        List<Post> listaPostsCompleta= new ArrayList<>();
        for (Discussion debate: listaDebatesCompleta) {
            listaPostsDebate = getListaPosts(token, debate);
            listaPostsCompleta.addAll(listaPostsDebate);
        }
        List<User> listaUsuarios=obtenerUsuarios(token,courseid);
        Map<Integer,Post> dudasAlumnosSinRespuesta= new HashMap<>();
        List<Post> listaRespuestasProfesores= new ArrayList<>();
        for (Post comentario:listaPostsCompleta) {
            if (esAlumno(listaUsuarios,comentario.getAuthor().getId())&&comentario.getMessage().contains("?")){
                dudasAlumnosSinRespuesta.put(comentario.getId(),comentario);
            }
            if (esProfesor(listaUsuarios,comentario.getAuthor().getId())&&comentario.isHasparent()){
                listaRespuestasProfesores.add(comentario);
            }
        }
        for (Post respuestaProfe:listaRespuestasProfesores) {
            if(dudasAlumnosSinRespuesta.containsKey(respuestaProfe.getParentid())&&
                    dudasAlumnosSinRespuesta.get(respuestaProfe.getParentid()).getTimecreated()-respuestaProfe.getTimecreated()<172800){
                dudasAlumnosSinRespuesta.remove(respuestaProfe.getParentid());
            }
        }
        return dudasAlumnosSinRespuesta.size()==0;
    }

    private static List<Post> getListaPosts(String token, Discussion debate) {
        RestTemplate restTemplate = new RestTemplate();
        PostList listaPostsDebate;
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_discussion_posts&moodlewsrestformat=json&wstoken="+ token +"&discussionid="+debate.getDiscussion();
        listaPostsDebate=restTemplate.getForObject(url,PostList.class);
        return listaPostsDebate.getPosts();
    }

    private static List<Discussion> getListaDebates(String token, Forum foro) {
        RestTemplate restTemplate = new RestTemplate();
        DiscussionList listaDebates;
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forum_discussions&moodlewsrestformat=json&wstoken="+ token +"&forumid="+foro.getId();
        listaDebates= restTemplate.getForObject(url,DiscussionList.class);
        return listaDebates.getDiscussions();
    }

    private static List<Forum> getListaForos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forums_by_courses&moodlewsrestformat=json&wstoken="+ token +"&courseids[0]="+courseid;
        Forum[] arrayForos= restTemplate.getForObject(url, Forum[].class);
        return new ArrayList<>(Arrays.asList(arrayForos));
    }
}
