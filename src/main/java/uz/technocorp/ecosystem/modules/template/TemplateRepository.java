package uz.technocorp.ecosystem.modules.template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.template.projection.TemplateView;
import uz.technocorp.ecosystem.modules.template.projection.TemplateViewBySelect;

import java.util.List;
import java.util.Optional;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {

    @Query("SELECT t FROM Template t")
    Page<TemplateView> getAll(Pageable pageable);

    @Query("SELECT t FROM Template t")
    List<TemplateViewBySelect> getAllBySelect();

    Optional<Template> findByType(TemplateType type);
}
