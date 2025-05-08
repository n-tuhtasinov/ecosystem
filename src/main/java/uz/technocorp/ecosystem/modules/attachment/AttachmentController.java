package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.shared.ApiResponse;

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
        String path = service.create(file, "registry-files");
        return ResponseEntity.ok(new ApiResponse(path));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> createPdfFromHtml(@RequestBody String htmlContent) {
        String path = service.createPdfFromHtml(htmlContent, "generated-documents");
        return ResponseEntity.ok(new ApiResponse(path));
    }

    @GetMapping("/content")
    public ResponseEntity<ApiResponse> getHtmlByPath(@RequestParam("path") String path) {
        String content = service.getHtmlByPath(path);
        return ResponseEntity.ok(new ApiResponse(content));
    }

    @PostMapping("/checklist-templates")
    public ResponseEntity<ApiResponse> createChecklistTemplates(@RequestBody MultipartFile file) {
        String path = service.create(file, "checklist-templates");
        return ResponseEntity.ok(new ApiResponse(path));
    }

    @PostMapping("/checklist")
    public ResponseEntity<ApiResponse> createChecklist(@RequestBody MultipartFile file) {
        String path = service.create(file, "checklists");
        return ResponseEntity.ok(new ApiResponse(path));
    }
}
