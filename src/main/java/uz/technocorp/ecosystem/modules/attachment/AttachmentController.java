package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.models.ApiResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 14.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService service;

    @PostMapping("/registry-files")
    public ResponseEntity<ApiResponse> createRegistryFiles(@RequestBody MultipartFile file) {
        try {
            return ResponseEntity.ok(new ApiResponse(service.create(file, "registry-files")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> createPdfFromHtml(@RequestBody String htmlContent) {
        return ResponseEntity.ok(new ApiResponse(service.createPdfFromHtml(htmlContent, "generated-documents")));
    }

    @GetMapping("/content")
    public ResponseEntity<ApiResponse> getHtmlByPath(@RequestParam("path") String path) {
        return ResponseEntity.ok(new ApiResponse(service.getHtmlByPath(path)));
    }

    @PostMapping("/checklist-templates")
    public ResponseEntity<ApiResponse> createChecklistTemplates(@RequestBody MultipartFile file) {
        try {
            return ResponseEntity.ok(new ApiResponse(service.create(file, "checklist-templates")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/checklist")
    public ResponseEntity<ApiResponse> createChecklist(@RequestBody MultipartFile file) {
        try {
            return ResponseEntity.ok(new ApiResponse(service.create(file, "checklist-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }
}
