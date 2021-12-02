package dls.examfrontend.controller;

import dls.examfrontend.dto.Student;
import dls.examfrontend.dto.Teacher;
import dls.examfrontend.service.Converter;
import dls.examfrontend.service.DBClient;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    DBClient DBClient;

    Converter converter = new Converter();

    @GetMapping("/getStudents")
    public List<Student> getStudents() {
        List<Student> allStudents = converter.convertStudentToList(DBClient.getAllStudents());
        return allStudents;
    }

    @GetMapping("/getAllStudents")
    public JSONArray getAllStudents() {
        return DBClient.getAllStudents();
    }
}
