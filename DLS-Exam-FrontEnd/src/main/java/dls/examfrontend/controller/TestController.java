package dls.examfrontend.controller;

import dls.examfrontend.dto.Student;
import dls.examfrontend.service.JsonConverters;
import dls.examfrontend.service.DBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    DBClient DBClient;

    JsonConverters converter = new JsonConverters();

    @GetMapping("/getStudents")
    public List<Student> getStudents() {
        List<Student> allStudents = converter.convertStudentToList(DBClient.allStudents());
        return allStudents;
    }

    @GetMapping("/getAllStudents")
    public ModelAndView getAllStudents() {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", converter.convertStudentToList(DBClient.allStudents()));
        return modelAndView;
    }
}
