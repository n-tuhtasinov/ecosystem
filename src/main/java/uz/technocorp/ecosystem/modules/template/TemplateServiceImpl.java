package uz.technocorp.ecosystem.modules.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.template.dto.PagingDto;
import uz.technocorp.ecosystem.modules.template.dto.TemplateDto;
import uz.technocorp.ecosystem.modules.template.dto.TemplateParamsDto;
import uz.technocorp.ecosystem.modules.template.form.TemplateForm;
import uz.technocorp.ecosystem.modules.user.enums.Direction;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

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
    public PagingDto<TemplateDto> getAllByParams(TemplateParamsDto params) {
        List<Sort.Order> orders = List.of(Sort.Order.asc("ord"));

        // Filter by type
        Specification<Template> hasType = (root, cq, cb) -> {
            if (params.getType() != null && !params.getType().isBlank()) {
                return cb.equal(root.get("type"), params.getType());
            }
            return null;
        };

        // Get template
        Page<Template> templates = repository.findAll(where(hasType), PageRequest.of(params.getPage() - 1, params.getLimit(), Sort.by(orders)));

        // Create paging dto
        PagingDto<TemplateDto> paging = new PagingDto<>((int) templates.getTotalElements(), params.getPage(), params.getLimit());
        paging.getItems().addAll(templates.stream().map(this::map).toList());

        return paging;
    }

    @Override
    public TemplateDto getById(Integer templateId) {
        return map(repository.findById(templateId).orElseThrow(() -> new ResourceNotFoundException("Template", "ID", templateId)));
    }

    @Override
    public Integer create(TemplateForm form) {
        return repository.save(Template.builder()
                .name(form.getName())
                .description(form.getDescription())
                .ord((int) (countAllTemplates() + 1))
                .type(Direction.valueOf(form.getType()))
                .content(form.getContent())
                .build()).getId();
    }

    @Override
    public Integer update(Integer templateId, TemplateForm form) {
        Template template = repository.findById(templateId).orElseThrow(() -> new ResourceNotFoundException("Template", "ID", templateId));

        template.setName(form.getName());
        template.setDescription(form.getDescription());
        template.setType(Direction.valueOf(form.getType()));
        template.setContent(form.getContent());

        return repository.save(template).getId();
    }

    @Override
    public void deleteById(Integer templateId) {
        repository.deleteById(templateId);
    }

    private long countAllTemplates() {
        return repository.count();
    }

    // MAPPER
    private TemplateDto map(Template template) {
        TemplateDto dto = new TemplateDto();

        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setType(template.getType().name());
        dto.setOrd(template.getOrd());
        dto.setContent(template.getContent());

        return dto;
    }
}
