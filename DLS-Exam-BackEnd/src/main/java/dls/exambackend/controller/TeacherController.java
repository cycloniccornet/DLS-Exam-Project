package dls.exambackend.controller;

import dls.exambackend.model.Teacher;
import dls.exambackend.repository.TeacherRepository;
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
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Teacher> retrieveTeacher(@PathVariable int id) throws NotFoundException {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new NotFoundException("Didnt find a teacher with ID: " + id);
        }
        EntityModel<Teacher> resource = EntityModel.of(teacher.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllTeachers());
        resource.add(linkTo.withRel("all-teachers"));

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveTeacher(id)).withSelfRel();
        resource.add(selfLink);
        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createTeacher(@RequestBody Teacher teacher)
    {
        Teacher savedTeacher = teacherRepository.save(teacher);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTeacher.getTeacherId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable int id) {
        teacherRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTeacher(@RequestBody Teacher teacher, @PathVariable int id)
    {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isEmpty())
            return ResponseEntity.notFound().build();
        teacher.setTeacherId(id);
        teacherRepository.save(teacher);
        return ResponseEntity.noContent().build();
    }

}
