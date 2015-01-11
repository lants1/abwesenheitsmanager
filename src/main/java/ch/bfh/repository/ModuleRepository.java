package ch.bfh.repository;

import ch.bfh.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Module entity.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("select m from Module m where m.type = ?1")
    Module getModuleByType(Integer type);
}
