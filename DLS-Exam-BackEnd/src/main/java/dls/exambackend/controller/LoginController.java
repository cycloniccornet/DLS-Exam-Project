package dls.exambackend.controller;

import dls.exambackend.model.Student;
import dls.exambackend.model.Teacher;
import dls.exambackend.repository.StudentRepository;
import dls.exambackend.repository.TeacherRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @PostMapping("/authenticateStudent")
    public Student authenticateStudentLogin(@RequestBody Student student, HttpServletRequest request) throws LoginException, NotFoundException {
        List<Student> tempStudents = studentRepository.findAll();
        for (Student current: tempStudents) {
            if (current.getMail().equals(student.getMail())) {
                if (current.getPassword().equals(student.getPassword())) {
                    return current;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN , "Password doesn't match the entered Email address.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email address not found.");
    }

    @PostMapping("/authenticateTeacher")
    public Teacher authenticateTeacherLogin(@RequestBody Teacher teacher, HttpServletRequest request) throws LoginException, NotFoundException {
        List<Teacher> tempTeachers = teacherRepository.findAll();
        for (Teacher current: tempTeachers) {
            if (current.getMail().equals(teacher.getMail())) {
                if (current.getPassword().equals(teacher.getPassword())) {
                    return current;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN , "Password doesn't match the entered Email address.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email address not found.");
    }
}
