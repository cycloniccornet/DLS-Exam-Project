package dls.exambackend.controller;

import dls.exambackend.model.Attendance;
import dls.exambackend.model.Session;
import dls.exambackend.repository.AttendanceRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    AttendanceRepository attendanceRepository;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/")
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Attendance> retrieveAttendance(@PathVariable int id) throws NotFoundException {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isEmpty()) {
            throw new NotFoundException("Didnt find a attendance with ID: " + id);
        }
        EntityModel<Attendance> resource = EntityModel.of(attendance.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllAttendances());
        resource.add(linkTo.withRel("all-attendances"));

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveAttendance(id)).withSelfRel();
        resource.add(selfLink);
        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createAttendance(@RequestBody Attendance attendance)
    {
        Attendance savedAttendance = attendanceRepository.save(attendance);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAttendance.getStudentId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteAttendance(@PathVariable int id) {
        attendanceRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAttendance(@RequestBody Attendance attendance, @PathVariable int id)
    {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if (attendanceOptional.isEmpty())
            return ResponseEntity.notFound().build();
        attendance.setAttendanceId(id);
        attendanceRepository.save(attendance);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enterSessionKey")
    public String enterSessionKey(@RequestParam String key, @RequestParam int student_id) {
        logger.info("Student with id "+student_id+" entered session key.");
        attendanceRepository.enterSessionKey(key, student_id);
        return "Success";
    }
}
