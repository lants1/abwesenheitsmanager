package ch.bfh.repository;

import ch.bfh.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.name = ?1")
    Student getStudentByName(String vornameLeerschlagNachname);
}
