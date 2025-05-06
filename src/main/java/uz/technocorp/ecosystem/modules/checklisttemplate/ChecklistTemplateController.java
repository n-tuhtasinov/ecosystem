package uz.technocorp.ecosystem.modules.checklisttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.ResponseMessage;
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
        try {
            service.update(id, dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findAll(@RequestParam(value = "name", defaultValue = "") String name) {
        try {
            List<ChecklistTemplateView> all = service.findAll(name);
            return ResponseEntity.ok(new ApiResponse(all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        try {
            Page<ChecklistTemplateView> all = service.getAll(page, size, name);
            return ResponseEntity.ok(new ApiResponse(all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
