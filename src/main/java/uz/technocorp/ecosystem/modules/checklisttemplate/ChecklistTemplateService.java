package uz.technocorp.ecosystem.modules.checklisttemplate;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.checklisttemplate.dto.ChecklistTemplateDto;
import uz.technocorp.ecosystem.modules.checklisttemplate.view.ChecklistTemplateView;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface ChecklistTemplateService {

    void create(ChecklistTemplateDto dto);
    void update(Integer id, ChecklistTemplateDto dto);
    void delete(Integer id);
    Page<ChecklistTemplateView> getAll(int page, int size, String name);
    List<ChecklistTemplateView> findAll(String name);

}
