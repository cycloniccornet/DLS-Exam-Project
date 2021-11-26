package dls.examfrontend.controller;

import dls.examfrontend.dto.LoginDTO;
import dls.examfrontend.dto.Student;
import dls.examfrontend.dto.Teacher;
import dls.examfrontend.service.Converter;
import dls.examfrontend.service.DBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    Converter converter = new Converter();

    @Autowired
    DBClient DBClient;

    @GetMapping("/login")
    public ModelAndView loginPage(HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("student") != null || session.getAttribute("teacher") != null) {
            response.sendRedirect("/");
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("student");
        return modelAndView;
    }

    @PostMapping("/login")
    public RedirectView studentLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute LoginDTO loginDTO) {
        Student loginStudent = null;
        try {
            loginStudent = converter.
                    convertStudentToModel(DBClient.authenticateStudent(converter.convertLoginToStudent(loginDTO)));
        } catch (Exception e) {

        }
        if (loginStudent != null) {
            logger.info("Student Login Successful: Setting session for current Student.");
            request.getSession().setAttribute("student", loginStudent);
            logger.info("Session set: " + loginStudent);
            return new RedirectView("/testStudentSession");
        }
        Teacher loginTeacher = converter.
                convertTeacherToModel(DBClient.authenticateTeacher(converter.convertLoginToTeacher(loginDTO)));
        if (loginTeacher != null) {
            logger.info("Teacher Login Successful: Setting session for current Teacher.");
            request.getSession().setAttribute("teacher", loginTeacher);
            logger.info("Session set: " + loginTeacher);
            return new RedirectView("/testTeacherSession");
        }
        return new RedirectView("/login");
    }

    @GetMapping("/")
    public String main() {
        return "Now you're here.";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new RedirectView("/login");
    }
}
