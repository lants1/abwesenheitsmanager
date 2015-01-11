package ch.bfh.service;

import ch.bfh.Application;
import ch.bfh.domain.*;
import ch.bfh.repository.*;
import org.junit.Before;
import org.junit.After;
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
    private StudentRepository studentRepository;

    @Inject
    private AbwesenheitsmanagerService abwesenheitsmanagerService;

    @Before
    public void initTest() {
        Student student = new Student();
        student.setName("hoschi");
        student.setPassword("pw");
        student.setUsername("poschi");
        studentRepository.saveAndFlush(student);

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
        lesson1.setStudent(student);
        Lesson lesson2 = new Lesson();
        lesson2.setVistied(true);
        lesson2.setModule(mod);
        lesson2.setStudent(student);
        Lesson lesson3 = new Lesson();
        lesson3.setVistied(true);
        lesson3.setModule(mod);
        lesson3.setStudent(student);
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

    }

    @Test
    public void testFindFinishedModules() {
        boolean finishedModuleOk = false;
        boolean finishedModuleNOk = false;

        List<Module> modules = abwesenheitsmanagerService.findAllFinishedModules();
        for(Module modFin: modules){
            if(modFin.getType() == 99){
                finishedModuleOk = true;
            }
            if(modFin.getType() == 98){
                finishedModuleNOk = true;
            }
        }
        assertThat(finishedModuleOk).isTrue();
        assertThat(finishedModuleNOk).isFalse();
    }

    @Test
    public void testGetVisitedLessonsByModuleTypeAndStudent() {
        assertThat(abwesenheitsmanagerService.getVisitedLessonsByModuleTypeAndStudent(99, "hoschi")).hasSize(3);
        assertThat(abwesenheitsmanagerService.getVisitedLessonsByModuleTypeAndStudent(99, "phoschi")).hasSize(0);
    }
}
