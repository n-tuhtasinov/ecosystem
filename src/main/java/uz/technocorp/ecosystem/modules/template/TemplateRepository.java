package uz.technocorp.ecosystem.modules.template;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer>, JpaSpecificationExecutor<Template> {

    long count();
}
