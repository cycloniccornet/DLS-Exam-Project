package dls.exambackend.controller;

import dls.exambackend.model.Subject;
import dls.exambackend.repository.SubjectRepository;
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
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Subject> retrieveSubject(@PathVariable int id) throws NotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isEmpty()) {
            throw new NotFoundException("Didnt find a subject with ID: " + id);
        }
        EntityModel<Subject> resource = EntityModel.of(subject.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllSubjects());
        resource.add(linkTo.withRel("all-subjects"));

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveSubject(id)).withSelfRel();
        resource.add(selfLink);
        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSubject(@RequestBody Subject subject)
    {
        Subject savedSubject = subjectRepository.save(subject);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSubject.getSubjectId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable int id) {
        subjectRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubject(@RequestBody Subject subject, @PathVariable int id)
    {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if (subjectOptional.isEmpty())
            return ResponseEntity.notFound().build();
        subject.setSubjectId(id);
        subjectRepository.save(subject);
        return ResponseEntity.noContent().build();
    }
}
