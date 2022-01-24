package es.ubu.lsi;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ubu.lsi.model.*;
import es.ubu.lsi.model.Date;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class WebServiceClient {

    public static FileReader reader;
    public static Properties properties;
    public static int UMBRAL_NUM_FORMATOS = 4;
    public static int ANIDAMIENTO_EXCESIVO = 4;
    public static int TIEMPO_RESPUESTA_FOROS = 172800;
    public static double PORCENTAJE_MIN_COMENTARIOS = 0.8;
    public static int TIEMPO_CORRECCION_TAREAS = 604800;
    public static double PORCENTAJE_MIN_RESPUESTAS = 0.6;

    static {
        try {
            reader = new FileReader("config");
            properties=new Properties();
            properties.load(reader);
            UMBRAL_NUM_FORMATOS= Integer.parseInt(properties.getProperty("umbral_num_formatos"));
            ANIDAMIENTO_EXCESIVO = Integer.parseInt(properties.getProperty("anidamiento_excesivo"));
            TIEMPO_RESPUESTA_FOROS = Integer.parseInt(properties.getProperty("tiempo_respuesta_foros"));
            PORCENTAJE_MIN_COMENTARIOS = Double.parseDouble(properties.getProperty("porcentaje_min_comentarios"));
            TIEMPO_CORRECCION_TAREAS = Integer.parseInt(properties.getProperty("tiempo_correccion_tareas"));
            PORCENTAJE_MIN_RESPUESTAS = Double.parseDouble(properties.getProperty("porcentaje_min_respuestas"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class).split("\"", 0)[3];
    }

    public static List<User> obtenerUsuarios(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_enrol_get_enrolled_users&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid;
        //guardarComoJSON(new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, User[].class))),"Listausuarios"+courseid);
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
        //guardarComoJSON(restTemplate.getForObject(url,CourseList.class).getCourses(),"Listacursos");
        return restTemplate.getForObject(url,CourseList.class).getCourses();
    }

    public static Course obtenerCursoPorId(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_courses_by_field&moodlewsrestformat=json&wstoken="+token+"&field=id&value="+courseid;
        return restTemplate.getForObject(url,CourseList.class).getCourses().get(0);
    }

    public static boolean tieneGrupos(List<Group> listaGrupos){
        return listaGrupos.size()!=0;
    }

    public static List<Group> obtenerListaGrupos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_group_get_course_groups&moodlewsrestformat=json&wstoken="+ token +"&courseid="+ courseid;
        List<Group> listaGrupos= new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, Group[].class)));
        //guardarComoJSON(listaGrupos,"Listagrupos"+courseid);
        return listaGrupos;
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
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_completion_get_activities_completion_status&moodlewsrestformat=json&wstoken="+ token +"&courseid="+ courseid +"&userid="+idProfesor;
        StatusList listaEstados=restTemplate.getForObject(url,StatusList.class);
        return listaEstados;
    }

    public static boolean estaProgresoActivado(StatusList listaEstados) {
        List<Status> estados=listaEstados.getStatuses();
        if(estados==null||estados.size()==0){return true;}
        return estados.get(0).isHascompletion();
    }

    public static boolean estaCorregidoATiempo(List<Assignment> tareasConNotas, Map<Integer, Integer> mapaFechasLimite){
        if(mapaFechasLimite.size()==0){return true;}
        if (tareasConNotas!=null) {
            for (Assignment tarea : tareasConNotas) {
                List<Grade> notas = tarea.getGrades();
                for (Grade nota : notas) {
                    if(Objects.equals(nota.getGrade(), "")){nota.setGrade("-1.00000");}
                    if (nota.getTimemodified() - mapaFechasLimite.get(tarea.getId()) > TIEMPO_CORRECCION_TAREAS ||
                            (System.currentTimeMillis() / 1000L) - mapaFechasLimite.get(tarea.getId()) > TIEMPO_CORRECCION_TAREAS && Float.parseFloat(nota.getGrade()) < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<Assignment> obtenerTareasConNotas(String token, Map<Integer, Integer> mapaFechasLimite) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder("https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_grades&moodlewsrestformat=json&wstoken=" + token);
        int contador=0;
        for (Integer id: mapaFechasLimite.keySet()) {
            url.append("&assignmentids[").append(contador).append("]=").append(id);
        }
        List<Assignment> tareasConNotas= restTemplate.getForObject(url.toString(),AssignmentList.class).getAssignments();
        //guardarComoJSON(tareasConNotas,"Listatareasconnotas"+courseid);
        return tareasConNotas;
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
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_assign_get_assignments&moodlewsrestformat=json&wstoken="+ token;
        CourseList listaCursos= restTemplate.getForObject(url,CourseList.class);
        List<Assignment> listaTareas= new ArrayList<>();
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
                    dudasAlumnosSinRespuesta.get(respuestaProfe.getParentid()).getTimecreated()-respuestaProfe.getTimecreated()< TIEMPO_RESPUESTA_FOROS){
                dudasAlumnosSinRespuesta.remove(respuestaProfe.getParentid());
            }
        }
        return dudasAlumnosSinRespuesta.size()==0;
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
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_discussion_posts&moodlewsrestformat=json&wstoken="+ token +"&discussionid="+debate.getDiscussion();
        listaPostsDebate=restTemplate.getForObject(url,PostList.class);
        return listaPostsDebate.getPosts();
    }

    public static List<Discussion> obtenerListaDebates(String token, Forum foro) {
        RestTemplate restTemplate = new RestTemplate();
        DiscussionList listaDebates;
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forum_discussions&moodlewsrestformat=json&wstoken="+ token +"&forumid="+foro.getId();
        listaDebates= restTemplate.getForObject(url,DiscussionList.class);
        return listaDebates.getDiscussions();
    }

    public static List<Forum> obtenerListaForos(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_forum_get_forums_by_courses&moodlewsrestformat=json&wstoken="+ token +"&courseids[0]="+courseid;
        Forum[] arrayForos= restTemplate.getForObject(url, Forum[].class);
        return new ArrayList<>(Arrays.asList(arrayForos));
    }

    public static boolean usaSurveys(List<Survey> listaEncuestas){
        return listaEncuestas.size()!=0;
    }

    public static List<Survey> obtenerSurveys(String token, int courseid) {
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_survey_get_surveys_by_courses&moodlewsrestformat=json&wstoken="+ token +"&courseids[0]="+ courseid;
        SurveyList listaEncuestas = restTemplate.getForObject(url, SurveyList.class);
        //guardarComoJSON(listaEncuestas.getSurveys(),"Listasurveys"+courseid);
        return listaEncuestas.getSurveys();
    }

    public static boolean alumnosEnGrupos(List<User> listaUsuarios){
        List<User> listaUsuariosHuerfanos = obtenerAlumnosSinGrupo(listaUsuarios);
        return listaUsuariosHuerfanos.size()==0;
    }

    public static List<User> obtenerAlumnosSinGrupo(List<User> listaUsuarios) {
        List<User> listaUsuariosHuerfanos=new ArrayList<>();
        for (User usuario:listaUsuarios) {
            if (usuario.getGroups().size()==0&&esAlumno(listaUsuarios,usuario.getId())){
                listaUsuariosHuerfanos.add(usuario);
            }
        }
        return listaUsuariosHuerfanos;
    }


    public static List<Table> obtenerCalificadores(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=gradereport_user_get_grades_table&moodlewsrestformat=json&wstoken="+ token +"&courseid="+courseid;
        TableList listaCalificadores = restTemplate.getForObject(url, TableList.class);
        //guardarComoJSON(listaCalificadores.getTables(),"Listacalificadores"+courseid);
        return listaCalificadores.getTables();
    }

    public static boolean anidamientoCalificadorAceptable(List<Table> listaCalificadores){
        if (listaCalificadores.size()==0){
            return false;
        }
        return listaCalificadores.get(0).getMaxdepth()< ANIDAMIENTO_EXCESIVO;
    }

    public static boolean calificadorMuestraPonderacion(List<Table> listaCalificadores){
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

    public static boolean hayRetroalimentacion(List<Table> listaCalificadores){
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
        return (float)contadorRetroalimentacion/(float)contadorTuplasComentables> PORCENTAJE_MIN_COMENTARIOS;
    }

    public static boolean esNotaMaxConsistente(List<Table> listaCalificadores){
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
        //guardarComoJSON(restTemplate.getForObject(url, ResourceList.class).getResources(),"Listarecursos"+courseid);
        return restTemplate.getForObject(url, ResourceList.class).getResources();
    }

    public static boolean estanActualizadosRecursos(List<Resource> listaRecursosDesfasados){
        return listaRecursosDesfasados.size()==0;
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

    public static List<Module> obtenerListaModulos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_contents&moodlewsrestformat=json&wstoken="+token+"&courseid="+courseid;
        List<Section> listaSecciones= new ArrayList<>(Arrays.asList(restTemplate.getForObject(url, Section[].class)));
        List<Module> listaModulos=new ArrayList<>();
        for (Section seccion:listaSecciones) {
            listaModulos.addAll(seccion.getModules());
        }
        //guardarComoJSON(listaModulos,"Listamodulos"+courseid);
        return listaModulos;
    }

    public static boolean sonFechasCorrectas(List<Module> listaModulosMalFechados){
        return listaModulosMalFechados.size()==0;
    }

    public static List<Module> obtenerModulosMalFechados(Course curso, List<Module> listaModulos) {
        int startdate=curso.getStartdate();
        int enddate=curso.getEnddate();
        List<Date> dates;
        List<Module> listaModulosMalFechados=new ArrayList<>();
        int opendate=0;
        int duedate=0;
        for (Module modulo:listaModulos) {
            if (modulo.getDates().size()>0){
                if (modulo.getDates().size()==1){
                    duedate=modulo.getDates().get(0).getTimestamp();
                    if(duedate>enddate&&enddate!=0||duedate<startdate){
                        listaModulosMalFechados.add(modulo);
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
                        listaModulosMalFechados.add(modulo);
                    }
                }
            }
        }
        return listaModulosMalFechados;
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
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_feedbacks_by_courses&moodlewsrestformat=json&wstoken="+token+"&courseids[0]="+courseid;
        FeedbackList listaFeedbacks= restTemplate.getForObject(url, FeedbackList.class);
        return listaFeedbacks.getFeedbacks();
    }

    //https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_responses_analysis&moodlewsrestformat=json&wstoken=320b59f9c79728d64e9dd5ce93f0b3dc&feedbackid=12
    public static List<ResponseAnalysis> obtenerAnalisis(String token, int courseid){
        List<Feedback> listaFeedbacks=obtenerFeedbacks(token, courseid);
        RestTemplate restTemplate = new RestTemplate();
        List<ResponseAnalysis> listaAnalisis= new ArrayList<>();
        String url="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=mod_feedback_get_responses_analysis&moodlewsrestformat=json&wstoken="+token+"&feedbackid=";
        for (Feedback feedback:listaFeedbacks) {
            listaAnalisis.add(restTemplate.getForObject(url+feedback.getId(),ResponseAnalysis.class));
        }
        //guardarComoJSON(listaAnalisis,"Listaanalisis"+courseid);
        return listaAnalisis;
    }





    public static boolean respondenFeedbacks(List<ResponseAnalysis> listaAnalisis, List<User> usuarios){
        int nAlumnos=numeroAlumnos(usuarios);
        for (ResponseAnalysis analisis:listaAnalisis) {
            if ((float)(analisis.getTotalattempts()+analisis.getTotalanonattempts())/(float) nAlumnos< PORCENTAJE_MIN_RESPUESTAS){
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
        String url="";
        for (Assignment tarea:listaTareas) {
            url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_course_module&moodlewsrestformat=json&wstoken="+token+"&cmid="+tarea.getCmid();
            listaModulosTareas.add(restTemplate.getForObject(url,CourseModuleWrapper.class).getCm());
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

    public static boolean hayVariedadFormatos(List<Module> listaModulos){
        List<String> formatosVistos=new ArrayList<>();
        for (Module modulo:listaModulos) {
            if ("book,resource,folder,imscp,label,page,url".contains(modulo.getModname())&&!formatosVistos.contains(modulo.getModname())){
                formatosVistos.add(modulo.getModname());
            }
        }
        return formatosVistos.size()>= UMBRAL_NUM_FORMATOS;
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
