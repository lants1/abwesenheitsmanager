package ch.bfh.service;

import ch.bfh.domain.Lesson;
import ch.bfh.domain.Module;
import ch.bfh.repository.LessonRepository;
import ch.bfh.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AbwesenheitsmanagerService {

    private final Logger log = LoggerFactory.getLogger(AbwesenheitsmanagerService.class);

    @Inject
    private ModuleRepository moduleRepository;

    @Inject
    private LessonRepository lessonRepository;


    public List<Module> findAllFinishedModules() {
        List<Module> modules = moduleRepository.findAll();
        List<Module> result = new ArrayList<Module>();
        for(Module mod : modules){
            List<Lesson> lessons = getVisitedLessonsByModuleType(mod.getType());
            if(lessons.size() > mod.getMinLessons()){
                result.add(mod);
            }
        }
        return result;
    }

    public List<Module> findAllOpenModules() {
        List<Module> modules = moduleRepository.findAll();
        List<Module> result = new ArrayList<Module>();
        for(Module mod : modules){
            List<Lesson> lessons = getVisitedLessonsByModuleType(mod.getType());
            if(lessons.size() < mod.getMinLessons()){
                result.add(mod);
            }
        }
        return result;
    }

    public List<Lesson> getVisitedLessonsByModuleType(Integer type){
        List<Lesson> lessons = lessonRepository.findAll();
        List<Lesson> result = new ArrayList<Lesson>();
        for(Lesson lesson : lessons){
            if(lesson.getVistied() && lesson.getModule().getType().intValue() == type.intValue()){
                result.add(lesson);
            }
        }
        return result;
    }
}
