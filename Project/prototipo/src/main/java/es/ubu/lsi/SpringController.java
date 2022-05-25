package es.ubu.lsi;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStream;


@Controller
public class SpringController{
    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/list")
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

    @GetMapping("/error")
    public String error() {
        return "error";
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

    @GetMapping(
            value = "/atardecer.jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] atardecer() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/atardecer.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/Logo.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] logo() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/Logo.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/FullLogo.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] fullLogo() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/FullLogo.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/Cross.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] cross() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/Cross.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/Check.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] check() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/Check.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/eye-slash.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] eyeslash() throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("images/eye-slash.png");
        return IOUtils.toByteArray(in);
    }
}
