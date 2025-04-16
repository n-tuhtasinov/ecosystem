package uz.technocorp.ecosystem.modules.template;

import uz.technocorp.ecosystem.modules.template.dto.PagingDto;
import uz.technocorp.ecosystem.modules.template.dto.TemplateDto;
import uz.technocorp.ecosystem.modules.template.dto.TemplateParamsDto;
import uz.technocorp.ecosystem.modules.template.form.TemplateForm;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
public interface TemplateService {

    Integer create(TemplateForm form);

    Integer update(Integer templateId, TemplateForm form);

    void deleteById(Integer templateId);

    PagingDto<TemplateDto> getAllByParams(TemplateParamsDto params);

    TemplateDto getById(Integer templateId);
}
