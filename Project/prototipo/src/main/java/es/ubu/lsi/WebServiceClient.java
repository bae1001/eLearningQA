package es.ubu.lsi;

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

    public static String callFunction(String function, String token) throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("https://school.moodledemo.net/webservice/rest/server.php?wsfunction="+function+"&moodlewsrestformat=json&wstoken="+token, String.class);
        String url ="https://school.moodledemo.net/webservice/rest/server.php?wsfunction="+function+"&moodlewsrestformat=json&wstoken="+token;
        ParameterizedTypeReference<List<Map<String,Object>>> responseType =
                new ParameterizedTypeReference<List<Map<String,Object>>>() {};
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, new RequestEntity(HttpMethod.GET, new URI(url)), responseType, new HashMap<String,String>());
        List<Map<String, Object>> listaCursos=(List<Map<String,Object>>)response.getBody();
        String texto = "<table border=\"1\">";
        for (Map<String, Object> curso : listaCursos) {
            texto+="<tr><td>"+(String)curso.get("fullname")+"</td></tr>";
        }
        texto+="</table>";
        //return response.getBody().toString();
        return texto;
    }
}
