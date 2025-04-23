package uz.technocorp.ecosystem.modules.template;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
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
@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService service;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAll(@RequestParam Map<String, String> params) {
        Page<TemplateView> regions = service.getAll(params);
        return ResponseEntity.ok(new ApiResponse(regions));
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer templateId) {
        Template template = service.getById(templateId);
        return ResponseEntity.ok(new ApiResponse(template));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAllBySelect() {
        List<TemplateViewBySelect> templates = service.getAllBySelect();
        return ResponseEntity.ok(new ApiResponse(templates));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TemplateForm form) {
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED, service.create(form)));
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer templateId, @Valid @RequestBody TemplateEditForm form) {
        form.setId(templateId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED, service.update(form)));
    }

    @PatchMapping("/content/{templateId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer templateId, @Valid @RequestBody ContentForm form) {
        form.setId(templateId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED, service.updateContent(form)));
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer templateId) {
        service.deleteById(templateId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

}
