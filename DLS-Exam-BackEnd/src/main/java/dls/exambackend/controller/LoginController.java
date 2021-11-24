package dls.exambackend.controller;

import dls.exambackend.model.Student;
import dls.exambackend.model.Teacher;
import dls.exambackend.repository.StudentRepository;
import dls.exambackend.repository.TeacherRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @PostMapping("/authenticateStudent")
    public Student authenticateStudentLogin(@RequestBody Student student) {
        logger.info("Endpoint called: '/authenticateStudent'");
        List<Student> tempStudents = studentRepository.findAll();
        for (Student current: tempStudents) {
            if (current.getMail().equals(student.getMail())) {
                if (current.getPassword().equals(student.getPassword())) {
                    logger.info("User status: Student with ID "+current.getStudentId()+" found - Login Successful!");
                    return current;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN , "Password doesn't match the entered Email address.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email address not found.");
    }

    @PostMapping("/authenticateTeacher")
    public Teacher authenticateTeacherLogin(@RequestBody Teacher teacher) {
        logger.info("Endpoint called: '/authenticateTeacher'");
        List<Teacher> tempTeachers = teacherRepository.findAll();
        for (Teacher current: tempTeachers) {
            if (current.getMail().equals(teacher.getMail())) {
                if (current.getPassword().equals(teacher.getPassword())) {
                    logger.info("User status: Teacher with ID "+current.getTeacherId()+" found - Login Successful!");
                    return current;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN , "Password doesn't match the entered Email address.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email address not found.");
    }
}
