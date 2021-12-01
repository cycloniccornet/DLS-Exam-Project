package dls.examfrontend.controller;

import dls.examfrontend.service.DBClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TeacherController {

    @Autowired
    DBClient dbClient;

    @GetMapping("/teacher")
    public ModelAndView teacherPage(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("teacher");
        modelAndView.addObject(session.getAttribute("teacher"));
        return modelAndView;
    }

    @PostMapping("/setSessionKey")
    public String setSessionKey() {
        String status = dbClient.setSessionKey();
        System.out.println(status);
        return status;
    }
}
