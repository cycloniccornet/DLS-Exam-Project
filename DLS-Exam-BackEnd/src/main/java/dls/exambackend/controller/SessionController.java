package dls.exambackend.controller;

import dls.exambackend.model.Session;
import dls.exambackend.repository.SessionRepository;
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
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;

    @GetMapping("/")
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Session> retrieveSession(@PathVariable int id) throws NotFoundException {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new NotFoundException("Didnt find a session with ID: " + id);
        }
        EntityModel<Session> resource = EntityModel.of(session.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllSessions());
        resource.add(linkTo.withRel("all-sessions"));

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveSession(id)).withSelfRel();
        resource.add(selfLink);
        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSession(@RequestBody Session session)
    {
        Session savedStudent = sessionRepository.save(session);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getSessionId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable int id) {
        sessionRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSession(@RequestBody Session session, @PathVariable int id)
    {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        if (sessionOptional.isEmpty())
            return ResponseEntity.notFound().build();
        session.setSessionId(id);
        sessionRepository.save(session);
        return ResponseEntity.noContent().build();
    }
}
