package es.ubu.lsi;

import es.ubu.lsi.model.*;
import es.ubu.lsi.model.Date;
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
        List<Assignment> listaTareas = obtenerListaTareas(token, courseid);
        Map<Integer, Integer> mapaFechasLimite= new HashMap<>();
        for (Assignment tarea:listaTareas) {
            if(tarea.getDuedate()!=0){
                mapaFechasLimite.put(tarea.getId(),tarea.getDuedate());
            }
        }
        if(mapaFechasLimite.size()==0){return true;}
        StringBuilder url = new StringBuilder("https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_grades&moodlewsrestformat=json&wstoken=" + token);
        int contador=0;
        for (Integer id:mapaFechasLimite.keySet()) {
            url.append("&assignmentids[").append(contador).append("]=").append(id);
        }
        List<Assignment> tareasConNotas= restTemplate.getForObject(url.toString(),AssignmentList.class).getAssignments();
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

    private static List<Assignment> obtenerListaTareas(String token, int courseid) {
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
        List<Forum> listaForos= obtenerListaForos(token, courseid);
        List<Discussion> listaDebates;
        List<Discussion> listaDebatesCompleta= new ArrayList<>();
        for (Forum foro:listaForos) {
            listaDebates = obtenerListaDebates(token, foro);
            listaDebatesCompleta.addAll(listaDebates);
        }
        List<Post> listaPostsDebate;
        List<Post> listaPostsCompleta= new ArrayList<>();
        for (Discussion debate: listaDebatesCompleta) {
            listaPostsDebate = obtenerListaPosts(token, debate);
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

    private static List<Post> obtenerListaPosts(String token, Discussion debate) {
        RestTemplate restTemplate = new RestTemplate();
        PostList listaPostsDebate;
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_discussion_posts&moodlewsrestformat=json&wstoken="+ token +"&discussionid="+debate.getDiscussion();
        listaPostsDebate=restTemplate.getForObject(url,PostList.class);
        return listaPostsDebate.getPosts();
    }

    private static List<Discussion> obtenerListaDebates(String token, Forum foro) {
        RestTemplate restTemplate = new RestTemplate();
        DiscussionList listaDebates;
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forum_discussions&moodlewsrestformat=json&wstoken="+ token +"&forumid="+foro.getId();
        listaDebates= restTemplate.getForObject(url,DiscussionList.class);
        return listaDebates.getDiscussions();
    }

    private static List<Forum> obtenerListaForos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forums_by_courses&moodlewsrestformat=json&wstoken="+ token +"&courseids[0]="+courseid;
        Forum[] arrayForos= restTemplate.getForObject(url, Forum[].class);
        return new ArrayList<>(Arrays.asList(arrayForos));
    }

    public static boolean usaSurveys(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_survey_get_surveys_by_courses&moodlewsrestformat=json&wstoken="+ token +"&courseids[0]="+courseid;
        SurveyList listaEncuestas = restTemplate.getForObject(url, SurveyList.class);
        return listaEncuestas.getSurveys().size()!=0;
    }

    public static boolean alumnosEnGrupos(String token, int courseid){
        List<User> listaUsuarios=obtenerUsuarios(token, courseid);
        List<User> listaUsuariosHuerfanos=new ArrayList<>();
        for (User usuario:listaUsuarios) {
            if (usuario.getGroups().size()==0&&esAlumno(listaUsuarios,usuario.getId())){
                listaUsuariosHuerfanos.add(usuario);
            }
        }
        return listaUsuariosHuerfanos.size()==0;
    }

    public static List<Table> obtenerCalificadores(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=gradereport_user_get_grades_table&moodlewsrestformat=json&wstoken="+ token +"&courseid="+courseid;
        TableList listaCalificadores = restTemplate.getForObject(url, TableList.class);
        return listaCalificadores.getTables();
    }

    public static boolean anidamientoCalificadorAceptable(String token, int courseid){
        List<Table> listaCalificadores=obtenerCalificadores(token, courseid);
        if (listaCalificadores.size()==0){
            return false;
        }
        return listaCalificadores.get(0).getMaxdepth()<4;
    }

    public static boolean calificadorMuestraPonderacion(String token, int courseid){
        List<Table> listaCalificadores=obtenerCalificadores(token, courseid);
        if (listaCalificadores.size()==0){
            return false;
        }
        for (Tabledata tabledata:listaCalificadores.get(0).getTabledata()) {
            if (tabledata.getWeight()!=null){
                return true;
            }
        }
        return false;
    }

    public static boolean hayRetroalimentacion(String token, int courseid){
        List<Table> listaCalificadores=obtenerCalificadores(token, courseid);
        int contadorRetroalimentacion=0;
        int contadorTuplasComentables=0;
        if (listaCalificadores.size()==0){
            return false;
        }
        for (Table calificador:listaCalificadores) {
            for (Tabledata tabledata:calificador.getTabledata()) {
                if (tabledata.getItemname().getMyclass().contains("item ")){
                    contadorTuplasComentables++;
                    if (tabledata.getFeedback().getContent().length()>6){
                        contadorRetroalimentacion++;
                    }
                }
            }
        }
        return (float)contadorRetroalimentacion/(float)contadorTuplasComentables>0.8;
    }

    public static boolean esNotaMaxConsistente(String token, int courseid){
        List<Table> listaCalificadores=obtenerCalificadores(token, courseid);
        String rango="";
        if (listaCalificadores.size()==0){
            return false;
        }
        for (Tabledata tabledata:listaCalificadores.get(0).getTabledata()) {
            if (tabledata.getRange()!=null){
                if (rango==""){
                    rango=tabledata.getRange().getContent();
                }
                if (!rango.equals(tabledata.getRange().getContent())){
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Resource> obtenerRecursos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_resource_get_resources_by_courses&moodlewsrestformat=json&wstoken="+token+"&courseids[0]="+courseid;
        return restTemplate.getForObject(url, ResourceList.class).getResources();
    }

    public static boolean estanActualizadosRecursos(String token, int courseid){
        Course curso=obtenerCursoPorId(token, courseid);
        List<Resource> listaRecursos=obtenerRecursos(token, courseid);
        int fechaUmbral=curso.getStartdate()-15780000;
        for (Resource recurso:listaRecursos) {
            for (Contentfile archivo: recurso.getContentfiles()) {
                if(archivo.getTimemodified()<fechaUmbral){
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Module> obtenerListaModulos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_contents&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid;
        List<Section> listaSecciones= new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, Section[].class)));
        List<Module> listaModulos=new ArrayList<>();
        for (Section seccion:listaSecciones) {
            listaModulos.addAll(seccion.getModules());
        }
        return listaModulos;
    }

    public static boolean sonFechasCorrectas(String token, int courseid){
        Course curso=obtenerCursoPorId(token, courseid);
        int startdate=curso.getStartdate();
        int enddate=curso.getEnddate();
        List<Date> dates;
        List<Module> listaModulos=obtenerListaModulos(token, courseid);
        int opendate=0;
        int duedate=0;
        for (Module modulo:listaModulos) {
            if (modulo.getDates().size()>0){
                if (modulo.getDates().size()==1){
                    duedate=modulo.getDates().get(0).getTimestamp();
                    if(duedate>enddate&&enddate!=0||duedate<startdate){
                        return false;
                    }
                }else{
                    dates=modulo.getDates();
                    for (Date fecha:dates) {
                        if (fecha.getLabel().contains("pened")){
                            opendate=fecha.getTimestamp();
                        }else{
                            duedate=fecha.getTimestamp();
                        }
                    }
                    if(opendate>enddate&&enddate!=0||opendate<startdate||duedate>enddate&&enddate!=0||duedate<startdate|| opendate>duedate){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int numeroAlumnos(String token, int courseid){
        List<User> usuarios=obtenerUsuarios(token, courseid);
        int contadorAlumnos=0;
        for (User usuario:usuarios) {
            for (Role rol: usuario.getRoles()) {
                if (rol.getRoleid() == 5) {
                        contadorAlumnos++;
                }
            }
        }
        return contadorAlumnos;
    }

    public static List<Feedback> obtenerFeedbacks(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_feedbacks_by_courses&moodlewsrestformat=json&wstoken="+token+"&courseids[0]="+courseid;
        FeedbackList listaFeedbacks= restTemplate.getForObject(url, FeedbackList.class);
        return listaFeedbacks.getFeedbacks();
    }

    public static int numeroNoRespondientes(String token, int courseid, int feedbackid){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_non_respondents&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid+"&feedbackid="+feedbackid;
        NonRespondentList listaNoRespondientes= restTemplate.getForObject(url, NonRespondentList.class);
        return listaNoRespondientes.getTotal();
    }

    public static boolean respondenFeedbacks(String token, int courseid){
        int nAlumnos=numeroAlumnos(token, courseid);
        List<Feedback> listaFeedbacks=obtenerFeedbacks(token, courseid);
        for (Feedback feedback:listaFeedbacks) {
            if ((float)numeroNoRespondientes(token, courseid,feedback.getId())/(float) nAlumnos>0.4){
                return false;
            }
        }
        return true;
    }

    public static boolean hayTareasGrupales(String token, int courseid){
        List<Assignment> listaTareas= obtenerListaTareas(token, courseid);
        for (Assignment tarea:listaTareas) {
            if(tarea.getTeamsubmission()==1){
                return true;
            }
        }
        return false;
    }

    public static CourseModule obtenerModulo(String token, int cmid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_course_module&moodlewsrestformat=json&wstoken="+token+"&cmid="+cmid;
        return restTemplate.getForObject(url,CourseModuleWrapper.class).getCm();
    }

    public static boolean muestraCriterios(String token, int courseid){
        List<Assignment> listaTareas= obtenerListaTareas(token, courseid);
        CourseModule modulo;
        for (Assignment tarea:listaTareas) {
            modulo=obtenerModulo(token, tarea.getCmid());
            for (Advancedgrading metodoAvanzado: modulo.getAdvancedgrading()) {
                if (metodoAvanzado.getMethod()!=null){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hayVariedadFormatos(String token, int courseid){
        List<Module> listaModulos=obtenerListaModulos(token, courseid);
        List<String> formatosVistos=new ArrayList<>();
        for (Module modulo:listaModulos) {
            if ("book,resource,folder,imscp,label,page,url".contains(modulo.getModname())&&!formatosVistos.contains(modulo.getModname())){
                formatosVistos.add(modulo.getModname());
            }
        }
        return formatosVistos.size()>=4;
    }
}
