package dls.exambackend.controller;

import dls.exambackend.model.Student;
import dls.exambackend.repository.StudentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Student> retrieveStudent(@PathVariable int id) throws NotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new NotFoundException("Didnt find a student with ID: " + id);
        }
        EntityModel<Student> resource = EntityModel.of(student.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllStudents());
        resource.add(linkTo.withRel("all-students"));

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveStudent(id)).withSelfRel();
        resource.add(selfLink);
        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createStudent(@RequestBody Student student)
    {
        Student savedStudent = studentRepository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getStudentId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty())
            return ResponseEntity.notFound().build();
        student.setStudentId(id);
        studentRepository.save(student);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getSessionStudents/{sessionKey}")
    public List<Student> getSessionStudents(@PathVariable String sessionKey) {
        return studentRepository.getSessionStudents(sessionKey);
    }
}
