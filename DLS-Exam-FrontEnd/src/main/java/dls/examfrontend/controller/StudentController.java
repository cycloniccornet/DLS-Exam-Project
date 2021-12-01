package dls.examfrontend.controller;

import dls.examfrontend.service.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class StudentController {

    @GetMapping("/student")
    public ModelAndView studentPage(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject(session.getAttribute("student"));
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView errorPage() {
        return new ModelAndView("error");
    }
}
