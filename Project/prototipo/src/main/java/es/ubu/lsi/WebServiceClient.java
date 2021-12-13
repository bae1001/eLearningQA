package es.ubu.lsi;

import es.ubu.lsi.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServiceClient {
    public static String login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String token = restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class).split("\"", 0)[3];
        //String token = restTemplate.getForObject("https://school.moodledemo.net/login/token.php?username="+username+"&password="+password+"&service=moodle_mobile_app", String.class);
        return token;
    }

    public static List<Curso> obtenerCursos(String token){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_enrolled_courses_by_timeline_classification&moodlewsrestformat=json&wstoken="+token+"&classification=inprogress";
        //String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_course_get_recent_courses&moodlewsrestformat=json&wstoken="+token;
        ParameterizedTypeReference<ListaCursos> responseType =
                new ParameterizedTypeReference<ListaCursos>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ListaCursos listaCursosConVariablesExtra=(ListaCursos)response.getBody();
        List<Curso> listaCursos=listaCursosConVariablesExtra.getCourses();
        return listaCursos;
    }

    public static boolean tieneGrupos(String token, int courseid){
        RestTemplate restTemplate = new RestTemplate();
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction=core_group_get_course_groups&moodlewsrestformat=json&wstoken="+token+"&courseid="+Integer.toString(courseid);
        ParameterizedTypeReference<List<Grupo>> responseType =
                new ParameterizedTypeReference<List<Grupo>>() {};
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<Grupo> listaGrupos=(List<Grupo>)response.getBody();
        return listaGrupos.size()!=0;
    }


}
