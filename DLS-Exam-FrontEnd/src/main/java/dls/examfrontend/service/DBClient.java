package dls.examfrontend.service;

import dls.examfrontend.dto.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("DLS-Exam-BackEnd")
public interface DBClient {

    // Student endpoints

    @RequestMapping(value = "/students/", method = RequestMethod.GET)
    JSONArray getAllStudents();

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    JSONObject getStudentById(@PathVariable int id);

    @RequestMapping(value = "/students/", method = RequestMethod.POST)
    JSONObject createStudent(@RequestBody Student student);

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    void deleteStudent(@PathVariable int id);

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    JSONObject updateStudent(@PathVariable int id, @RequestBody Student student);

    @RequestMapping(value = "/students/getSessionStudents/{sessionKey}", method = RequestMethod.GET)
    JSONArray getSessionStudents(@PathVariable String sessionKey);

    // Teacher endpoints.

    @RequestMapping(value = "/teachers/", method = RequestMethod.GET)
    JSONArray getAllTeachers();

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET)
    JSONObject getTeacherById(@PathVariable int id);

    @RequestMapping(value = "/teachers/", method = RequestMethod.POST)
    JSONObject createTeacher(@RequestBody Teacher teacher);

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE)
    void deleteTeacher(@PathVariable int id);

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.PUT)
    JSONObject updateTeacher(@PathVariable int id, @RequestBody Teacher teacher);

    // Subject endpoints.

    @RequestMapping(value = "/subjects/", method = RequestMethod.GET)
    JSONArray getAllSubjects();

    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET)
    JSONObject getSubjectById(@PathVariable int id);

    @RequestMapping(value = "/subjects/", method = RequestMethod.POST)
    JSONObject createSubject(@RequestBody Subject subject);

    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.DELETE)
    void deleteSubject(@PathVariable int id);

    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.PUT)
    JSONObject updateSubject(@PathVariable int id, @RequestBody Subject subject);

    // Session endpoints.

    @RequestMapping(value = "/sessions/", method = RequestMethod.GET)
    JSONArray getAllSessions();

    @RequestMapping(value = "/sessions/{id}", method = RequestMethod.GET)
    JSONObject getSessionById(@PathVariable int id);

    @RequestMapping(value = "/sessions/", method = RequestMethod.POST)
    JSONObject createSession(@RequestBody Session session);

    @RequestMapping(value = "/sessions/{id}", method = RequestMethod.DELETE)
    void deleteSession(@PathVariable int id);

    @RequestMapping(value = "/sessions/{id}", method = RequestMethod.PUT)
    JSONObject updateSession(@PathVariable int id, @RequestBody Session session);

    @RequestMapping(value = "/sessions/setSessionKey", method = RequestMethod.POST)
    String setSessionKey();

    @RequestMapping(value = "/sessions/resetSessionKey", method = RequestMethod.POST)
    void resetSessionKey();

    // Attendance endpoints.

    @RequestMapping(value = "/attendances/", method = RequestMethod.GET)
    JSONArray getAllAttendances();

    @RequestMapping(value = "/attendances/{id}", method = RequestMethod.GET)
    JSONObject getAttendanceById(@PathVariable int id);

    @RequestMapping(value = "/attendances/", method = RequestMethod.POST)
    JSONObject createAttendance(@RequestBody Attendance attendance);

    @RequestMapping(value = "/attendances/{id}", method = RequestMethod.DELETE)
    void deleteAttendance(@PathVariable int id);

    @RequestMapping(value = "/attendances/{id}", method = RequestMethod.PUT)
    JSONObject updateAttendance(@PathVariable int id, @RequestBody Attendance attendance);

    @RequestMapping(value = "/attendances/enterSessionKey", method = RequestMethod.POST)
    String enterSessionKey(@RequestParam String key, @RequestParam int student_id);

    // Login endpoints.

    @RequestMapping(value = "/login/authenticateStudent", method = RequestMethod.POST)
    JSONObject authenticateStudent(@RequestBody Student student);

    @RequestMapping(value = "/login/authenticateTeacher", method = RequestMethod.POST)
    JSONObject authenticateTeacher(@RequestBody Teacher teacher);
}
