package ch.bfh.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import ch.bfh.Application;
import ch.bfh.domain.Lesson;
import ch.bfh.repository.LessonRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LessonResource REST controller.
 *
 * @see LessonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class LessonResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

   private static final DateTime DEFAULT_DATE = new DateTime(0L);
   private static final DateTime UPDATED_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    private static final Boolean DEFAULT_VISTIED = false;
    private static final Boolean UPDATED_VISTIED = true;
    private static final Boolean DEFAULT_RATING = false;
    private static final Boolean UPDATED_RATING = true;

    @Inject
    private LessonRepository lessonRepository;

    private MockMvc restLessonMockMvc;

    private Lesson lesson;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LessonResource lessonResource = new LessonResource();
        ReflectionTestUtils.setField(lessonResource, "lessonRepository", lessonRepository);
        this.restLessonMockMvc = MockMvcBuilders.standaloneSetup(lessonResource).build();
    }

    @Before
    public void initTest() {
        lesson = new Lesson();
        lesson.setDate(DEFAULT_DATE);
        lesson.setVistied(DEFAULT_VISTIED);
        lesson.setRating(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createLesson() throws Exception {
        // Validate the database is empty
        assertThat(lessonRepository.findAll()).hasSize(4);

        // Create the Lesson
        restLessonMockMvc.perform(post("/app/rest/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(5);
        Lesson testLesson = lessons.iterator().next();
        assertThat(testLesson.getVistied()).isEqualTo(true);
    }

    @Test
    @Transactional
    public void getAllLessons() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get all the lessons
        restLessonMockMvc.perform(get("/app/rest/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(4))
                .andExpect(jsonPath("$.[0].vistied").value(true));
    }

    @Test
    @Transactional
    public void getLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(get("/app/rest/lessons/{id}", lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.vistied").value(DEFAULT_VISTIED.booleanValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLesson() throws Exception {
        // Get the lesson
        restLessonMockMvc.perform(get("/app/rest/lessons/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Update the lesson
        lesson.setDate(UPDATED_DATE);
        lesson.setVistied(UPDATED_VISTIED);
        lesson.setRating(UPDATED_RATING);
        restLessonMockMvc.perform(post("/app/rest/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(5);
        Lesson testLesson = lessons.get(4);
        assertThat(testLesson.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLesson.getVistied()).isEqualTo(UPDATED_VISTIED);
        assertThat(testLesson.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void deleteLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(delete("/app/rest/lessons/{id}", lesson.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(4);
    }
}
