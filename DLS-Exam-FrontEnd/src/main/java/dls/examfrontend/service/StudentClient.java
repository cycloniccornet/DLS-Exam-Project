package dls.examfrontend.service;

import dls.examfrontend.dto.Student;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("DLS-Exam-BackEnd")
public interface StudentClient {

    @RequestMapping(value = "/students/", method = RequestMethod.GET)
    JSONArray allStudents();

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    JSONObject getOneStudent(@PathVariable int id);

    @RequestMapping(value = "/students/", method = RequestMethod.POST)
    JSONObject createStudent(@RequestBody Student student);

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    void deleteStudent(@PathVariable int id);

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    JSONObject updateStudent(@PathVariable int id, @RequestBody Student student);
}
