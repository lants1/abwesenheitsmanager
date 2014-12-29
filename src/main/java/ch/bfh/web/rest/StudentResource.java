package ch.bfh.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.bfh.domain.Student;
import ch.bfh.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Student.
 */
@RestController
@RequestMapping("/app")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    @Inject
    private StudentRepository studentRepository;

    /**
     * POST  /rest/students -> Create a new student.
     */
    @RequestMapping(value = "/rest/students",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Student student) {
        log.debug("REST request to save Student : {}", student);
        studentRepository.save(student);
    }

    /**
     * GET  /rest/students -> get all the students.
     */
    @RequestMapping(value = "/rest/students",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Student> getAll() {
        log.debug("REST request to get all Students");
        return studentRepository.findAll();
    }

    /**
     * GET  /rest/students/:id -> get the "id" student.
     */
    @RequestMapping(value = "/rest/students/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student> get(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        return Optional.ofNullable(studentRepository.findOne(id))
            .map(student -> new ResponseEntity<>(
                student,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/students/:id -> delete the "id" student.
     */
    @RequestMapping(value = "/rest/students/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentRepository.delete(id);
    }
}
