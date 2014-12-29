package ch.bfh.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.bfh.domain.Lesson;
import ch.bfh.repository.LessonRepository;
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
 * REST controller for managing Lesson.
 */
@RestController
@RequestMapping("/app")
public class LessonResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);

    @Inject
    private LessonRepository lessonRepository;

    /**
     * POST  /rest/lessons -> Create a new lesson.
     */
    @RequestMapping(value = "/rest/lessons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Lesson lesson) {
        log.debug("REST request to save Lesson : {}", lesson);
        lessonRepository.save(lesson);
    }

    /**
     * GET  /rest/lessons -> get all the lessons.
     */
    @RequestMapping(value = "/rest/lessons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lesson> getAll() {
        log.debug("REST request to get all Lessons");
        return lessonRepository.findAll();
    }

    /**
     * GET  /rest/lessons/:id -> get the "id" lesson.
     */
    @RequestMapping(value = "/rest/lessons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lesson> get(@PathVariable Long id) {
        log.debug("REST request to get Lesson : {}", id);
        return Optional.ofNullable(lessonRepository.findOne(id))
            .map(lesson -> new ResponseEntity<>(
                lesson,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/lessons/:id -> delete the "id" lesson.
     */
    @RequestMapping(value = "/rest/lessons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        lessonRepository.delete(id);
    }
}
