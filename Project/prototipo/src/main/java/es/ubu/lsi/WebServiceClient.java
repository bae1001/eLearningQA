package es.ubu.lsi;

import es.ubu.lsi.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class WebServiceClient {
    public static String login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String token = restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class).split("\"", 0)[3];
        //String token = restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class);
        return token;
    }

    public static List<User> obtenerUsuarios(String token, int courseid) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_enrol_get_enrolled_users&moodlewsrestformat=json&wstoken="+token+"&courseid="+Integer.toString(courseid);
        List<User> listaUsuarios=new ArrayList<User>(Arrays.asList(restTemplate.getForObject(new URI(url),User[].class)));
        return listaUsuarios;
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

    public static boolean esAlumno(List<User> usuarios, int userid) throws URISyntaxException {
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
        //String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_recent_courses&moodlewsrestformat=json&wstoken="+token;
        ParameterizedTypeReference<CourseList> responseType =
                new ParameterizedTypeReference<CourseList>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CourseList listaCursosConVariablesExtra=(CourseList)response.getBody();
        List<Course> listaCursos=listaCursosConVariablesExtra.getCourses();
        return listaCursos;
    }

    public static Course obtenerCursoPorId(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_courses_by_field&moodlewsrestformat=json&wstoken="+token+"&field=id&value="+Integer.toString(courseid);
        ParameterizedTypeReference<CourseList> responseType =
                new ParameterizedTypeReference<CourseList>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CourseList listaCursosConVariablesExtra=(CourseList)response.getBody();
        List<Course> listaCursos=listaCursosConVariablesExtra.getCourses();
        return listaCursos.get(0);
    }

    public static boolean tieneGrupos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_group_get_course_groups&moodlewsrestformat=json&wstoken="+token+"&courseid="+Integer.toString(courseid);
        ParameterizedTypeReference<List<Group>> responseType =
                new ParameterizedTypeReference<List<Group>>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<Group> listaGrupos=(List<Group>)response.getBody();
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
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_enrol_get_enrolled_users&moodlewsrestformat=json&wstoken="+token+"&courseid="+Integer.toString(courseid);
        ParameterizedTypeReference<List<User>> responseType =
                new ParameterizedTypeReference<List<User>>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<User> listaUsuarios=(List<User>)response.getBody();
        int idProfesor=listaUsuarios.get(0).getId();
        String url2 ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_completion_get_activities_completion_status&moodlewsrestformat=json&wstoken="+token+"&courseid="+Integer.toString(courseid)+"&userid="+Integer.toString(idProfesor);
        ParameterizedTypeReference<StatusList> responseType2 =
                new ParameterizedTypeReference<StatusList>() {};
        ResponseEntity response2 = null;
        try {
            response2 = restTemplate.exchange(url2, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url2)), responseType2, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(response2.toString());
        StatusList listaEstados=(StatusList) response2.getBody();
        return listaEstados.isHasCompletion();
    }

    public static boolean estaCorregidoATiempo(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_assignments&moodlewsrestformat=json&wstoken="+token;
        ParameterizedTypeReference<CourseList> responseType =
                new ParameterizedTypeReference<CourseList>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CourseList listaCursos=(CourseList)response.getBody();
        List<Assignment> listaTareas=new ArrayList<Assignment>();
        for (Course curso:listaCursos.getCourses()) {
            if(curso.getId()==courseid) {
                listaTareas.addAll(curso.getAssignments());
            }
        }
        Map<Integer, Integer> mapaFechasLimite=new HashMap<Integer, Integer>();
        for (Assignment tarea:listaTareas) {
            if(tarea.getDuedate()!=0){
                mapaFechasLimite.put(tarea.getId(),tarea.getDuedate());
            }
        }
        if(mapaFechasLimite.size()==0){return true;}
        String url2 ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_grades&moodlewsrestformat=json&wstoken="+token;
        int contador=0;
        for (Integer id:mapaFechasLimite.keySet()) {
            url2+="&assignmentids["+Integer.toString(contador)+"]="+Integer.toString(id);
        }
        ParameterizedTypeReference<AssignmentList> responseType2 =
                new ParameterizedTypeReference<AssignmentList>() {};
        ResponseEntity response2 = null;
        try {
            response2 = restTemplate.exchange(url2, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url2)), responseType2, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        AssignmentList listaTareasConNotas=(AssignmentList) response2.getBody();
        List<Assignment> tareasConNotas= listaTareasConNotas.getAssignments();
        if (tareasConNotas!=null) {
            for (Assignment tarea : tareasConNotas) {
                List<Grade> notas = tarea.getGrades();
                for (Grade nota : notas) {
                    if(nota.getGrade()==""){nota.setGrade("-1.00000");}
                    if (nota.getTimemodified() - mapaFechasLimite.get(tarea.getId()) > 604800 ||
                            (System.currentTimeMillis() / 1000l) - mapaFechasLimite.get(tarea.getId()) > 604800 && Float.parseFloat(nota.getGrade()) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean respondeATiempo(String token, int courseid) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forums_by_courses&moodlewsrestformat=json&wstoken="+token+"&courseids[0]="+Integer.toString(courseid);
        Forum[] arrayForos= restTemplate.getForObject(new URI(url), Forum[].class);
        List<Forum> listaForos= new ArrayList<Forum>(Arrays.asList(arrayForos));
        String url2="";
        DiscussionList listaDebates;
        List<Discussion> listaDebatesCompleta=new ArrayList<Discussion>();
        for (Forum foro:listaForos) {
            url2="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forum_discussions&moodlewsrestformat=json&wstoken="+token+"&forumid="+Integer.toString(foro.getId());
            listaDebates=restTemplate.getForObject(new URI(url2),DiscussionList.class);
            listaDebatesCompleta.addAll(listaDebates.getDiscussions());
        }
        String url3="";
        PostList listaPostsDebate;
        List<Post> listaPostsCompleta=new ArrayList<Post>();
        for (Discussion debate: listaDebatesCompleta) {
            url3="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_discussion_posts&moodlewsrestformat=json&wstoken="+token+"&discussionid="+Integer.toString(debate.getDiscussion());
            listaPostsDebate=restTemplate.getForObject(new URI(url3),PostList.class);
            listaPostsCompleta.addAll(listaPostsDebate.getPosts());
        }
        List<User> listaUsuarios=obtenerUsuarios(token,courseid);
        Map<Integer,Post> dudasAlumnosSinRespuesta=new HashMap<Integer,Post>();
        List<Post> listaRespuestasProfesores=new ArrayList<Post>();
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


}
