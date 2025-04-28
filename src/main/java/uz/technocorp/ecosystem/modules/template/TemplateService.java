package uz.technocorp.ecosystem.modules.template;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.template.form.ContentForm;
import uz.technocorp.ecosystem.modules.template.form.TemplateEditForm;
import uz.technocorp.ecosystem.modules.template.form.TemplateForm;
import uz.technocorp.ecosystem.modules.template.projection.TemplateView;
import uz.technocorp.ecosystem.modules.template.projection.TemplateViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
public interface TemplateService {

    Page<TemplateView> getAll(Map<String, String> params);

    List<TemplateViewBySelect> getAllBySelect();

    Template getById(Integer templateId);

    Template getByType(String type);

    Integer create(TemplateForm form);

    Integer update(TemplateEditForm form);

    Integer updateContent(ContentForm form);

    void deleteById(Integer templateId);
}
