package ch.bfh.repository;

import ch.bfh.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Module entity.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {

}
