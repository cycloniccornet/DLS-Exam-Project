package dls.examfrontend.controller;

import dls.examfrontend.dto.Student;
import dls.examfrontend.service.JsonConverters;
import dls.examfrontend.service.StudentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    StudentClient studentClient;

    @GetMapping("/login")
    public ModelAndView loginPage(HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("student") != null) {
            response.sendRedirect("/welcome");
        } else if (session.getAttribute("teacher") != null) {
            response.sendRedirect("/");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/studentLogin")
    public String studentLogin(HttpServletRequest request, @RequestBody Student student) {
        JsonConverters converter = new JsonConverters();
        Student loginStudent = converter.convertStudentToModel(studentClient.studentLogin(student));
        if (loginStudent != null) {
            logger.info("Login Successful: Setting session for current Student.");
            request.getSession().setAttribute("student", loginStudent);
            logger.info("Session set: " + loginStudent);
            return "200 OK - Session set.";
        }
        return "404 NOT FOUND - Session not set.";
    }

    @GetMapping("/testSession")
    public Student testSession(HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        return (Student) session.getAttribute("student");
    }
}
