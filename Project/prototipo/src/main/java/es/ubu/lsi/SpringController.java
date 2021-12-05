package es.ubu.lsi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SpringController{
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/list")
    public String list(@ModelAttribute String username) {
        return "list";
    }
}