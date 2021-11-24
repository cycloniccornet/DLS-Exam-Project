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

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    StudentClient studentClient;

    @GetMapping("/login")
    public ModelAndView loginPage(HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("student") != null || session.getAttribute("teacher") != null) {
            try {
                response.sendRedirect("/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("login");
    }

    @PostMapping("/studentLogin")
    public void studentLogin(HttpServletRequest request, @RequestBody Student current) {
        JsonConverters converter = new JsonConverters();
        Student student = converter.convertStudentToModel(studentClient.studentLogin(current));
        if (student != null) {
            logger.info("Login Successful: Setting session for current Student.");
            request.getSession().setAttribute("student", student);
        }
    }

    @GetMapping("/testSession")
    public Student testSession(HttpSession request) {

        // TODO - Fix issue with nothing returned from HttpSession.

        Student student = (Student) request.getAttribute("student");
        System.out.println(student);
        return (Student) request.getAttribute("student");
    }
}
