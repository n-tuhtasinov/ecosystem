package uz.technocorp.ecosystem.modules.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.shared.AppConstants;
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
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository repository;

    @Override
    public Page<TemplateView> getAll(Map<String, String> params) {
        Pageable pageable = PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "createdAt");

        return repository.getAll(pageable);
    }

    @Override
    public List<TemplateViewBySelect> getAllBySelect() {
        return repository.getAllBySelect();
    }

    @Override
    public Template getById(Integer templateId) {
        return repository.findById(templateId).orElseThrow(() -> new ResourceNotFoundException("Shablon", "ID", templateId));
    }

    @Override
    public Template getByType(String type) {
        return repository.findByType(TemplateType.valueOf(type)).orElse(null);
    }

    @Override
    public Integer create(TemplateForm form) {
        // Check if template with same type already exists
        if (getByType(form.getType()) != null) {
            throw new ResourceNotFoundException("Bu turdagi shablon mavjud : " + form.getType());
        }
        return repository.save(Template.builder()
                .name(form.getName())
                .description(form.getDescription())
                .type(TemplateType.valueOf(form.getType()))
                .content(form.getContent())
                .build()).getId();
    }

    @Override
    public Integer update(TemplateEditForm form) {
        Template template = repository.findById(form.getId()).orElseThrow(() -> new ResourceNotFoundException("Shablon", "ID", form.getId()));

        template.setName(form.getName());
        template.setDescription(form.getDescription());

        return repository.save(template).getId();
    }

    @Override
    public Integer updateContent(ContentForm form) {
        Template template = repository.findById(form.getId()).orElseThrow(() -> new ResourceNotFoundException("Shablon", "ID", form.getId()));

        template.setContent(form.getContent());

        return repository.save(template).getId();
    }

    @Override
    public void deleteById(Integer templateId) {
        repository.deleteById(templateId);
    }
}
