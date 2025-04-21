package uz.technocorp.ecosystem.modules.template;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
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
@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService service;

    @PostMapping("/paging")
    public ResponseEntity<ApiResponse> getAll(@RequestBody TemplateParamsDto params) {
        PagingDto<TemplateDto> regions = service.getAllByParams(params);
        return ResponseEntity.ok(new ApiResponse(regions));
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer templateId) {
        TemplateDto template = service.getById(templateId);
        return ResponseEntity.ok(new ApiResponse(template));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TemplateForm form) {
        Integer id = service.create(form);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED, id));
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer templateId, @Valid @RequestBody TemplateForm form) {
        Integer id = service.update(templateId, form);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED, id));
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer templateId) {
        service.deleteById(templateId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

}
