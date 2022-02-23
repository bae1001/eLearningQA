package es.ubu.lsi;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ubu.lsi.model.*;
import es.ubu.lsi.model.Date;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileReader;
import java.util.*;

public class WebServiceClient {



    private static String host = "https://school.moodledemo.net";
    private static int umbralNumFormatos = 4;
    private static int anidamientoExcesivo = 4;
    private static int tiempoRespuestaForos = 172800;
    private static double porcentajeMinComentarios = 0.8;
    private static int tiempoCorreccionTareas = 604800;
    private static double porcentajeMinRespuestas = 0.6;


    static {
        try {
            FileReader reader = new FileReader("config");
            Properties properties = new Properties();
            properties.load(reader);
            umbralNumFormatos = Integer.parseInt(properties.getProperty("format_num_threshold"));
            anidamientoExcesivo = Integer.parseInt(properties.getProperty("excessive_nesting"));
            tiempoRespuestaForos = Integer.parseInt(properties.getProperty("forum_response_time"));
            porcentajeMinComentarios = Double.parseDouble(properties.getProperty("min_comment_percentage"));
            tiempoCorreccionTareas = Integer.parseInt(properties.getProperty("assignment_grading_time"));
            porcentajeMinRespuestas = Double.parseDouble(properties.getProperty("min_feedback_answer_percentage"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHost(String host) {
        WebServiceClient.host = host;
    }

    public static String login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String token=restTemplate.getForObject(host + "/login/token.php?username=" +username+"&password="+password+"&service=moodle_mobile_app", String.class).split("\"", 0)[3];
        if (token==null){return "";}
        return token;
    }

    public static List<User> obtenerUsuarios(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=core_enrol_get_enrolled_users&moodlewsrestformat=json&wstoken=" +token+"&courseid="+courseid;
        //guardarComoJSON(new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, User[].class))),"Listausuarios"+courseid);
        ArrayList<User> users = new ArrayList<>();
        User[] userArray=restTemplate.getForObject(url, User[].class);
        if(userArray!=null){
            users = new ArrayList<>(Arrays.asList(userArray));
        }
        return users;
    }

    public static String obtenerNombreCompleto(String token, String username){
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=core_user_get_users_by_field&moodlewsrestformat=json&wstoken="+token+"&field=username&values[0]="+username;
        User[] usuarios=restTemplate.getForObject(url, User[].class);
        if (usuarios==null||usuarios[0]==null){return "John Doe";}
        return usuarios[0].getFullname();
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
        String url = host + "/webservice/rest/server.php?wsfunction=core_course_get_enrolled_courses_by_timeline_classification&moodlewsrestformat=json&wstoken=" +token+"&classification=inprogress";
        //guardarComoJSON(restTemplate.getForObject(url,CourseList.class).getCourses(),"Listacursos");
        CourseList listacursos=restTemplate.getForObject(url,CourseList.class);
        if (listacursos==null){return new ArrayList<>();}
        return listacursos.getCourses();
    }

    public static Course obtenerCursoPorId(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=core_course_get_courses_by_field&moodlewsrestformat=json&wstoken=" +token+"&field=id&value="+courseid;
        CourseList listacursos=restTemplate.getForObject(url,CourseList.class);
        if (listacursos==null){return new Course();}
        return listacursos.getCourses().get(0);
    }

    public static boolean tieneGrupos(List<Group> listaGrupos){
        return !listaGrupos.isEmpty();
    }

    public static List<Group> obtenerListaGrupos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=core_group_get_course_groups&moodlewsrestformat=json&wstoken=" + token +"&courseid="+ courseid;
        Group[] listaGrupos= restTemplate.getForObject(url, Group[].class);
        if (listaGrupos==null){return new ArrayList<>();}
        //guardarComoJSON(listaGrupos,"Listagrupos"+courseid);
        return new ArrayList<>(Arrays.asList(listaGrupos));
    }

    public static boolean sonVisiblesCondiciones(Course curso){
        if(curso.getShowcompletionconditions()!=null){
            return curso.getShowcompletionconditions();
        }
        return false;
    }

    public static StatusList obtenerListaEstados(String token, int courseid, List<User> listaUsuarios) {
        RestTemplate restTemplate = new RestTemplate();
        int idProfesor=listaUsuarios.get(0).getId();
        for (User usuario:listaUsuarios) {
            if(esProfesor(listaUsuarios,usuario.getId())){
                idProfesor=usuario.getId();
            }
        }
        String url = host + "/webservice/rest/server.php?wsfunction=core_completion_get_activities_completion_status&moodlewsrestformat=json&wstoken=" + token +"&courseid="+ courseid +"&userid="+idProfesor;
        StatusList listaEstados=restTemplate.getForObject(url,StatusList.class);
        if (listaEstados==null){return new StatusList();}
        return listaEstados;
    }

    public static boolean estaProgresoActivado(StatusList listaEstados) {
        List<Status> estados=listaEstados.getStatuses();
        if(estados==null||estados.isEmpty()){return true;}
        return estados.get(0).isHascompletion();
    }

    public static boolean estaCorregidoATiempo(List<Assignment> tareasConNotas, Map<Integer, Integer> mapaFechasLimite){
        if(mapaFechasLimite.isEmpty()){return true;}
        for (Assignment tarea : tareasConNotas) {
            List<Grade> notas = tarea.getGrades();
            for (Grade nota : notas) {
                if(Objects.equals(nota.getGrade(), "")){nota.setGrade("-1.00000");}
                if (nota.getTimemodified() - mapaFechasLimite.get(tarea.getId()) > tiempoCorreccionTareas ||
                        (System.currentTimeMillis() / 1000L) - mapaFechasLimite.get(tarea.getId()) > tiempoCorreccionTareas && Float.parseFloat(nota.getGrade()) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Assignment> obtenerTareasConNotas(String token, Map<Integer, Integer> mapaFechasLimite) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder(host + "/webservice/rest/server.php?wsfunction=mod_assign_get_grades&moodlewsrestformat=json&wstoken=" + token);
        int contador=0;
        for (Integer id: mapaFechasLimite.keySet()) {
            url.append("&assignmentids[").append(contador).append("]=").append(id);
        }
        AssignmentList tareasConNotas= restTemplate.getForObject(url.toString(),AssignmentList.class);
        if (tareasConNotas==null){return new ArrayList<>();}
        //guardarComoJSON(tareasConNotas,"Listatareasconnotas"+courseid);
        return tareasConNotas.getAssignments();
    }

    public static Map<Integer, Integer> generarMapaFechasLimite(List<Assignment> listaTareas) {
        Map<Integer, Integer> mapaFechasLimite= new HashMap<>();
        for (Assignment tarea: listaTareas) {
            if(tarea.getDuedate()!=0){
                mapaFechasLimite.put(tarea.getId(),tarea.getDuedate());
            }
        }
        return mapaFechasLimite;
    }

    public static List<Assignment> obtenerListaTareas(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=mod_assign_get_assignments&moodlewsrestformat=json&wstoken=" + token;
        CourseList listaCursos= restTemplate.getForObject(url,CourseList.class);
        List<Assignment> listaTareas= new ArrayList<>();
        if(listaCursos==null){return listaTareas;}
        for (Course curso:listaCursos.getCourses()) {
            if(curso.getId()== courseid) {
                listaTareas.addAll(curso.getAssignments());
            }
        }
        //guardarComoJSON(listaTareas,"Listatareas"+courseid);
        return listaTareas;
    }

    public static boolean respondeATiempo(List<User> listaUsuarios, List<Post> listaPostsCompleta){
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
                    dudasAlumnosSinRespuesta.get(respuestaProfe.getParentid()).getTimecreated()-respuestaProfe.getTimecreated()< tiempoRespuestaForos){
                dudasAlumnosSinRespuesta.remove(respuestaProfe.getParentid());
            }
        }
        return dudasAlumnosSinRespuesta.isEmpty();
    }

    public static List<Post> obtenerListaPosts(String token, int courseid) {
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
        //guardarComoJSON(listaPostsCompleta,"Listaposts"+courseid);
        return listaPostsCompleta;
    }

    public static List<Post> obtenerListaPosts(String token, Discussion debate) {
        RestTemplate restTemplate = new RestTemplate();
        PostList listaPostsDebate;
        String url= host + "/webservice/rest/server.php?wsfunction=mod_forum_get_discussion_posts&moodlewsrestformat=json&wstoken=" + token +"&discussionid="+debate.getDiscussion();
        listaPostsDebate=restTemplate.getForObject(url,PostList.class);
        if (listaPostsDebate==null){return new ArrayList<>();}
        return listaPostsDebate.getPosts();
    }

    public static List<Discussion> obtenerListaDebates(String token, Forum foro) {
        RestTemplate restTemplate = new RestTemplate();
        DiscussionList listaDebates;
        String url= host + "/webservice/rest/server.php?wsfunction=mod_forum_get_forum_discussions&moodlewsrestformat=json&wstoken=" + token +"&forumid="+foro.getId();
        listaDebates= restTemplate.getForObject(url,DiscussionList.class);
        if (listaDebates==null){return new ArrayList<>();}
        return listaDebates.getDiscussions();
    }

    public static List<Forum> obtenerListaForos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url= host + "/webservice/rest/server.php?wsfunction=mod_forum_get_forums_by_courses&moodlewsrestformat=json&wstoken=" + token +"&courseids[0]="+courseid;
        Forum[] arrayForos= restTemplate.getForObject(url, Forum[].class);
        if (arrayForos==null){return new ArrayList<>();}
        return new ArrayList<>(Arrays.asList(arrayForos));
    }

    public static boolean usaSurveys(List<Survey> listaEncuestas){
        return !listaEncuestas.isEmpty();
    }

    public static List<Survey> obtenerSurveys(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url= host + "/webservice/rest/server.php?wsfunction=mod_survey_get_surveys_by_courses&moodlewsrestformat=json&wstoken=" + token +"&courseids[0]="+ courseid;
        SurveyList listaEncuestas = restTemplate.getForObject(url, SurveyList.class);
        //guardarComoJSON(listaEncuestas.getSurveys(),"Listasurveys"+courseid);
        if (listaEncuestas==null){return new ArrayList<>();}
        return listaEncuestas.getSurveys();
    }

    public static boolean alumnosEnGrupos(List<User> listaUsuarios){
        List<User> listaUsuariosHuerfanos = obtenerAlumnosSinGrupo(listaUsuarios);
        return listaUsuariosHuerfanos.isEmpty();
    }

    public static List<User> obtenerAlumnosSinGrupo(List<User> listaUsuarios) {
        List<User> listaUsuariosHuerfanos=new ArrayList<>();
        for (User usuario:listaUsuarios) {
            if (usuario.getGroups().isEmpty()&&esAlumno(listaUsuarios,usuario.getId())){
                listaUsuariosHuerfanos.add(usuario);
            }
        }
        return listaUsuariosHuerfanos;
    }


    public static List<Table> obtenerCalificadores(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url= host + "/webservice/rest/server.php?wsfunction=gradereport_user_get_grades_table&moodlewsrestformat=json&wstoken=" + token +"&courseid="+courseid;
        TableList listaCalificadores = restTemplate.getForObject(url, TableList.class);
        //guardarComoJSON(listaCalificadores.getTables(),"Listacalificadores"+courseid);
        if (listaCalificadores==null){return new ArrayList<>();}
        return listaCalificadores.getTables();
    }

    public static boolean anidamientoCalificadorAceptable(List<Table> listaCalificadores){
        if (listaCalificadores.isEmpty()){
            return false;
        }
        return listaCalificadores.get(0).getMaxdepth()< anidamientoExcesivo;
    }

    public static boolean calificadorMuestraPonderacion(List<Table> listaCalificadores){
        if (listaCalificadores.isEmpty()){
            return false;
        }
        for (Tabledata tabledata:listaCalificadores.get(0).getTabledata()) {
            if (tabledata.getWeight()!=null){
                return true;
            }
        }
        return false;
    }

    public static boolean hayRetroalimentacion(List<Table> listaCalificadores){
        int contadorRetroalimentacion=0;
        int contadorTuplasComentables=0;
        if (listaCalificadores.isEmpty()){
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
        if(contadorTuplasComentables==0){return true;}
        return (float)contadorRetroalimentacion/(float)contadorTuplasComentables> porcentajeMinComentarios;
    }

    public static boolean esNotaMaxConsistente(List<Table> listaCalificadores){
        String rango="";
        if (listaCalificadores.isEmpty()){
            return false;
        }
        for (Tabledata tabledata:listaCalificadores.get(0).getTabledata()) {
            if (tabledata.getRange()!=null){
                if (rango.equals("")){
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
        String url = host + "/webservice/rest/server.php?wsfunction=mod_resource_get_resources_by_courses&moodlewsrestformat=json&wstoken=" +token+"&courseids[0]="+courseid;
        //guardarComoJSON(restTemplate.getForObject(url, ResourceList.class).getResources(),"Listarecursos"+courseid);
        ResourceList listaRecursos=restTemplate.getForObject(url, ResourceList.class);
        if (listaRecursos==null){return new ArrayList<>();}
        return listaRecursos.getResources();
    }

    public static boolean estanActualizadosRecursos(List<Resource> listaRecursosDesfasados){
        return listaRecursosDesfasados.isEmpty();
    }

    public static List<Resource> obtenerRecursosDesfasados(Course curso, List<Resource> listaRecursos) {
        List<Resource> listaRecursosDesfasados=new ArrayList<>();
        int fechaUmbral=curso.getStartdate()-15780000;
        for (Resource recurso:listaRecursos) {
            for (Contentfile archivo: recurso.getContentfiles()) {
                if(archivo.getTimemodified()<fechaUmbral){
                    listaRecursosDesfasados.add(recurso);
                }
            }
        }
        return listaRecursosDesfasados;
    }

    public static List<es.ubu.lsi.model.Module> obtenerListaModulos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url = host + "/webservice/rest/server.php?wsfunction=core_course_get_contents&moodlewsrestformat=json&wstoken=" +token+"&courseid="+courseid;
        Section[] arraySecciones=restTemplate.getForObject(url, Section[].class);
        List<Section> listaSecciones;
        if (arraySecciones==null){
            listaSecciones=new ArrayList<>();
        }else{
            listaSecciones= new ArrayList<>(Arrays.asList(arraySecciones));
        }
        List<es.ubu.lsi.model.Module> listaModulos=new ArrayList<>();
        for (Section seccion:listaSecciones) {
            listaModulos.addAll(seccion.getModules());
        }
        //guardarComoJSON(listaModulos,"Listamodulos"+courseid);
        return listaModulos;
    }

    public static boolean sonFechasCorrectas(List<es.ubu.lsi.model.Module> listaModulosMalFechados){
        return listaModulosMalFechados.isEmpty();
    }

    public static List<es.ubu.lsi.model.Module> obtenerModulosMalFechados(Course curso, List<es.ubu.lsi.model.Module> listaModulos) {
        int startdate=curso.getStartdate();
        int enddate=curso.getEnddate();
        List<Date> dates;
        List<es.ubu.lsi.model.Module> listaModulosMalFechados=new ArrayList<>();
        int opendate;
        int duedate;
        for (es.ubu.lsi.model.Module modulo:listaModulos) {
            opendate=0;
            duedate=0;
            dates=modulo.getDates();
            for (Date fecha:dates) {
                if (fecha.getLabel().contains("pened")){
                    opendate=fecha.getTimestamp();
                }else{
                    duedate=fecha.getTimestamp();
                }
            }
            if(!comprobarFechas(startdate, enddate, opendate, duedate)){
                listaModulosMalFechados.add(modulo);
            }
        }
        return listaModulosMalFechados;
    }

    public static boolean comprobarFechas(int startdate, int enddate, int opendate, int duedate){
        return (opendate==0||startdate<opendate&&(opendate<enddate||enddate==0))&&
                (duedate==0||startdate<duedate&&(duedate<enddate||enddate==0&&duedate>opendate));
    }

    public static int numeroAlumnos(List<User> usuarios){
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
        String url= host + "/webservice/rest/server.php?wsfunction=mod_feedback_get_feedbacks_by_courses&moodlewsrestformat=json&wstoken=" +token+"&courseids[0]="+courseid;
        FeedbackList listaFeedbacks= restTemplate.getForObject(url, FeedbackList.class);
        if (listaFeedbacks==null){return new ArrayList<>();}
        return listaFeedbacks.getFeedbacks();
    }

    //https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_responses_analysis&moodlewsrestformat=json&wstoken=320b59f9c79728d64e9dd5ce93f0b3dc&feedbackid=12
    public static List<ResponseAnalysis> obtenerAnalisis(String token, int courseid){
        List<Feedback> listaFeedbacks=obtenerFeedbacks(token, courseid);
        RestTemplate restTemplate = new RestTemplate();
        List<ResponseAnalysis> listaAnalisis= new ArrayList<>();
        String url= host + "/webservice/rest/server.php?wsfunction=mod_feedback_get_responses_analysis&moodlewsrestformat=json&wstoken=" +token+"&feedbackid=";
        for (Feedback feedback:listaFeedbacks) {
            listaAnalisis.add(restTemplate.getForObject(url+feedback.getId(),ResponseAnalysis.class));
        }
        //guardarComoJSON(listaAnalisis,"Listaanalisis"+courseid);
        return listaAnalisis;
    }





    public static boolean respondenFeedbacks(List<ResponseAnalysis> listaAnalisis, List<User> usuarios){
        int nAlumnos=numeroAlumnos(usuarios);
        if(nAlumnos==0){return true;}
        for (ResponseAnalysis analisis:listaAnalisis) {
            if ((float)(analisis.getTotalattempts()+analisis.getTotalanonattempts())/(float) nAlumnos< porcentajeMinRespuestas){
                return false;
            }
        }
        return true;
    }

    public static boolean hayTareasGrupales(List<Assignment> listaTareas){
        for (Assignment tarea:listaTareas) {
            if(tarea.getTeamsubmission()==1){
                return true;
            }
        }
        return false;
    }



    public static List<CourseModule> obtenerModulosTareas(String token, List<Assignment> listaTareas){
        RestTemplate restTemplate = new RestTemplate();
        List<CourseModule> listaModulosTareas=new ArrayList<>();
        CourseModuleWrapper wrapper;
        String url;
        for (Assignment tarea:listaTareas) {
            url = host + "/webservice/rest/server.php?wsfunction=core_course_get_course_module&moodlewsrestformat=json&wstoken=" +token+"&cmid="+tarea.getCmid();
            wrapper=restTemplate.getForObject(url,CourseModuleWrapper.class);
            if (wrapper!=null){
                listaModulosTareas.add(wrapper.getCm());
            }
        }
        //guardarComoJSON(listaModulosTareas,"Listamodulostareas"+listaTareas.get(0).getCourse());
        return listaModulosTareas;
    }

    public static boolean muestraCriterios(List<CourseModule> listaModulosTareas){
        for (CourseModule modulo:listaModulosTareas) {
            for (Advancedgrading metodoAvanzado: modulo.getAdvancedgrading()) {
                if (metodoAvanzado.getMethod()!=null){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hayVariedadFormatos(List<es.ubu.lsi.model.Module> listaModulos){
        List<String> formatosVistos=new ArrayList<>();
        for (es.ubu.lsi.model.Module modulo:listaModulos) {
            if ("book,resource,folder,imscp,label,page,url".contains(modulo.getModname())&&!formatosVistos.contains(modulo.getModname())){
                formatosVistos.add(modulo.getModname());
            }
        }
        return formatosVistos.size()>= umbralNumFormatos;
    }


    public static void guardarComoJSON(Object objeto, String nombre){
        String sep=File.separator;
        try {
            new ObjectMapper().writeValue(new File("."+sep+"src"+sep+"main"+sep+"resources"+sep+"json"+sep+nombre+".json"), objeto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
