package dls.examfrontend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HomeController {

    @GetMapping("/student")
    public ModelAndView studentPage(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject(session.getAttribute("student"));
        return modelAndView;
    }

    @GetMapping("/teacher")
    public ModelAndView teacherPage(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("teacher");
        modelAndView.addObject(session.getAttribute("teacher"));
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView errorPage() {
        return new ModelAndView("error");
    }
}
