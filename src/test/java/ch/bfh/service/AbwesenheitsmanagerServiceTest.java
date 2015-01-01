package ch.bfh.service;

import ch.bfh.Application;
import ch.bfh.domain.Lesson;
import ch.bfh.domain.Module;
import ch.bfh.domain.PersistentToken;
import ch.bfh.domain.User;
import ch.bfh.repository.LessonRepository;
import ch.bfh.repository.ModuleRepository;
import ch.bfh.repository.PersistentTokenRepository;
import ch.bfh.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the UserResource REST controller.
 *
 * @see ch.bfh.service.UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class AbwesenheitsmanagerServiceTest {

    @Inject
    private ModuleRepository moduleRepository;

    @Inject
    private LessonRepository lessonRepository;

    @Inject
    private AbwesenheitsmanagerService abwesenheitsmanagerService;

    @Test
    public void testRemoveOldPersistentTokens() {
        Module mod = new Module();
        mod.setMinLessons(1);
        mod.setType(99);
        mod.setName("test");
        mod.setPassed(false);
        moduleRepository.saveAndFlush(mod);

        Module mod2 = new Module();
        mod2.setMinLessons(5);
        mod2.setType(98);
        mod2.setName("test");
        mod2.setPassed(false);
        moduleRepository.saveAndFlush(mod2);

        mod = moduleRepository.getModuleByType(99);
        Lesson lesson1 = new Lesson();
        lesson1.setVistied(true);
        lesson1.setModule(mod);
        Lesson lesson2 = new Lesson();
        lesson2.setVistied(true);
        lesson2.setModule(mod);
        Lesson lesson3 = new Lesson();
        lesson3.setVistied(true);
        lesson3.setModule(mod);
        lessonRepository.saveAndFlush(lesson1);
        lessonRepository.saveAndFlush(lesson2);
        lessonRepository.saveAndFlush(lesson3);

        mod2 = moduleRepository.getModuleByType(98);
        Lesson lesson11 = new Lesson();
        lesson11.setVistied(true);
        lesson11.setModule(mod2);
        Lesson lesson22 = new Lesson();
        lesson22.setVistied(true);
        lesson22.setModule(mod2);
        Lesson lesson33 = new Lesson();
        lesson33.setVistied(true);
        lesson33.setModule(mod2);
        lessonRepository.saveAndFlush(lesson11);
        lessonRepository.saveAndFlush(lesson22);
        lessonRepository.saveAndFlush(lesson33);

        assertThat(abwesenheitsmanagerService.findAllFinishedModules()).hasSize(1);
    }
}
