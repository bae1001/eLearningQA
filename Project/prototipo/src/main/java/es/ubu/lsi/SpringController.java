package es.ubu.lsi;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import es.ubu.lsi.model.Course;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SpringController {

        @GetMapping({ "/", "/login" })
        public String login() {
                return "login";
        }

        @PostMapping("/list")
        public String list() {
                return "list";
        }

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

        @GetMapping("/about")
        public String about() {
                return "about";
        }

        @GetMapping("/error")
        public String error() {
                return "error";
        }

        @GetMapping(value = "/cabecera.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
        public @ResponseBody byte[] cabecera() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/cabecera.jpg");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/atardecer.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
        public @ResponseBody byte[] atardecer() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/atardecer.jpg");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/Logo.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] logo() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/Logo.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/FullLogo.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] fullLogo() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/FullLogo.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/Cross.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] cross() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/Cross.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/Check.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] check() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/Check.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/eye-slash.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] eyeslash() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/eye-slash.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/InformeFases.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] informeFases() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/InformeFases.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/Evolucion.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] evolucion() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/Evolucion.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/ListaCursos.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] listaCursos() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/ListaCursos.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/Login.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] loginpng() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/Login.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/escudoInfor.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] escudoInfor() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/escudoInfor.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/excelIcon.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] excelIcon() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/excelIcon.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/newIcon.png", produces = MediaType.IMAGE_PNG_VALUE)
        public @ResponseBody byte[] newIcon() throws IOException {
                InputStream in = getClass().getClassLoader()
                                .getResourceAsStream("images/new.png");
                return IOUtils.toByteArray(in);
        }

        @GetMapping(value = "/excel-report")
        public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
                Course course = (Course) request.getSession().getAttribute("course");
                ELearningQAFacade facade = (ELearningQAFacade) request.getSession().getAttribute("facade");
                int[] puntos = (int[]) request.getSession().getAttribute("puntos");
                String user = (String) request.getSession().getAttribute("user");
                response.setContentType("text/plain;base64");
                ByteArrayOutputStream generatedExcel = facade.writeExcel(course, puntos, user);
                if (generatedExcel != null) {
                        String base64EncodedString = Base64.getEncoder().encodeToString(generatedExcel.toByteArray());
                        response.getWriter().write(base64EncodedString);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.flushBuffer();
                } else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
        }

}
