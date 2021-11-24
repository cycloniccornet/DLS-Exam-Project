package dls.examfrontend.service;

import dls.examfrontend.dto.Student;
import dls.examfrontend.dto.Teacher;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @FeignClient("DLS-Exam-BackEnd")
public interface LoginClient {

    @RequestMapping(value = "/authenticateStudent", method = RequestMethod.POST)
    JSONObject studentLogin(@RequestBody Student student);

    @RequestMapping(value = "/authenticateTeacher", method = RequestMethod.POST)
    JSONObject teacherLogin(@RequestBody Teacher teacher);

}
