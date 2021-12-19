package dls.examfrontend.controller;

import dls.examfrontend.dto.Attendance;
import dls.examfrontend.dto.Session;
import dls.examfrontend.dto.Student;
import dls.examfrontend.dto.Subject;
import dls.examfrontend.service.Converter;
import dls.examfrontend.service.DBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    DBClient dbClient;

    Converter converter = new Converter();
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/student")
    public ModelAndView studentPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject(session.getAttribute("student"));
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView errorPage() {
        return new ModelAndView("error");
    }

    @PostMapping("/enterSessionKey/{key}")
    public String enterSessionKey(@PathVariable String key, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        return dbClient.enterSessionKey(key, student.getStudentId());
    }

    @GetMapping("/getAllSubjects")
    public List<Subject> getAllSubjects() {
        logger.info("Getting all subjects.");
        return converter.convertSubjectToList(dbClient.getAllSubjects());
    }


    @GetMapping("/getAttendanceBySubjectId/{subjectId}")
    public List<Session> getAllAttendancesBySubjectId(@PathVariable int subjectId, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        List<Attendance> allAttendances = converter.convertAttendanceToList(dbClient.getAllAttendances());
        List<Session> allSessions = converter.convertSessionToList(dbClient.getAllSessions());

        List<Session> studentSessions = new ArrayList<>();

        for (Attendance currentAttendance: allAttendances) {
            if (currentAttendance.getStudentId() == student.getStudentId()) {
                for (Session currentSession: allSessions) {
                    if (currentSession.getSubjectId() == subjectId && currentAttendance.getSessionId() == currentSession.getSessionId()) {
                        studentSessions.add(currentSession);
                    }
                }
            }
        }
        return studentSessions;
    }

    @GetMapping("/getAllSessionsBySubjectId/{subjectId}")
    public List<Session> getAllSessionsBySubjectId(@PathVariable int subjectId) {
        List<Session> allSessions = converter.convertSessionToList(dbClient.getAllSessions());
        List<Session> studentSessions = new ArrayList<>();
        for (Session currentSession: allSessions) {
            if (currentSession.getSubjectId() == subjectId) {
                studentSessions.add(currentSession);
            }
        }
        return studentSessions;
    }
}
