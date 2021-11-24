package dls.examfrontend.controller;

import dls.examfrontend.model.Student;
import dls.examfrontend.service.JsonConverters;
import dls.examfrontend.service.StudentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    StudentClient studentClient;

    JsonConverters converter = new JsonConverters();

    @GetMapping("/getStudents")
    public List<Student> getStudents() {
        List<Student> allStudents = converter.convertStudentToList(studentClient.allStudents());
        return allStudents;
    }

    @GetMapping("/getAllStudents")
    public ModelAndView getAllStudents() {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", converter.convertStudentToList(studentClient.allStudents()));
        return modelAndView;
    }
}
