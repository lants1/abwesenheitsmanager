package ch.bfh.repository;

import ch.bfh.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
