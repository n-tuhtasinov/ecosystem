package uz.technocorp.ecosystem.modules.checklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.checklist.view.ChecklistView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
public interface ChecklistRepository extends JpaRepository<Checklist, UUID> {

    List<Checklist> findAllByTemplateId(Integer id);

    @Query(value = """
            select cast(chl.id as varchar) as id,
            chl.path as path,
            template_id as templateId,
            tpl.name as name
            from checklist chl
            join checklist_template tpl on chl.template_id = tpl.id
            where tin = :tin
            """, nativeQuery = true)
    Page<ChecklistView> findAllChecklist(Pageable pageable, Long tin);

    @Query(value = """
            select cast(chl.id as varchar) as id,
            chl.path as path,
            template_id as templateId,
            tpl.name as name
            from checklist chl
            join checklist_template tpl on chl.template_id = tpl.id
            where profile_id = :profileId
            """, nativeQuery = true)
    Page<ChecklistView> findAllChecklist(Pageable pageable, UUID profileId);
}
