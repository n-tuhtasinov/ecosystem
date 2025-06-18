package uz.technocorp.ecosystem.modules.checklisttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.checklisttemplate.dto.ChecklistTemplateDto;
import uz.technocorp.ecosystem.modules.checklisttemplate.view.ChecklistTemplateView;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/checklist-templates")
@RequiredArgsConstructor
public class ChecklistTemplateController {

    private final ChecklistTemplateService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChecklistTemplateDto dto) {
        try {
            service.create(dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ChecklistTemplateDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateActivate(@PathVariable Integer id) {
        service.updateActivate(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable Integer id) {
        ChecklistTemplateView checklistTemplate = service.getChecklistTemplate(id);
        return ResponseEntity.ok(new ApiResponse(checklistTemplate));
    }

    @GetMapping("/select")
    public ResponseEntity<?> findAll(@RequestParam(value = "name", defaultValue = "") String name) {
        List<ChecklistTemplateView> all = service.findAll(name);
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "active") Boolean active) {
        Page<ChecklistTemplateView> all = service.getAll(page, size, name, active);
        return ResponseEntity.ok(new ApiResponse(all));
    }
}
