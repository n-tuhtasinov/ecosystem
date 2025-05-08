package uz.technocorp.ecosystem.modules.checklisttemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.checklisttemplate.view.ChecklistTemplateView;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface ChecklistTemplateRepository extends JpaRepository<ChecklistTemplate, Integer> {


    List<ChecklistTemplate> name(String name);

    @Query(value = """
            select id,
            name,
            path
            from checklist_template
            where name ilike concat('%', name, '%')
            """, nativeQuery = true)
    Page<ChecklistTemplateView> getAllByName(Pageable pageable, String name);

    @Query(value = """
            select id,
            name,
            path
            from checklist_template
            where name ilike concat('%', name, '%')
            """, nativeQuery = true)
    List<ChecklistTemplateView> findAllByName(String name);
}
