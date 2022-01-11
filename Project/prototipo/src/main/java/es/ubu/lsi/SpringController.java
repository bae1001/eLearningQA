package es.ubu.lsi;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


@Controller
public class SpringController{
    @GetMapping("/")
    public String login() {
        return "login";
    }

    //@PostMapping("/list")
    @RequestMapping(value="/list", method = { RequestMethod.POST,RequestMethod.GET })
    public String list() {
        return "list";
    }

    //@ModelAttribute String username
    @GetMapping("/informe")
    public String informe() {
        return "informe";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/manual")
    public String manual() {
        return "manual";
    }

    @GetMapping(
            value = "/cabecera.jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] cabecera() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/cabecera.jpg");
        return IOUtils.toByteArray(in);
    }
}
