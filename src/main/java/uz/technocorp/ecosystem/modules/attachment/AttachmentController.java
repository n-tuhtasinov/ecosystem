package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> createRegistryFiles(@RequestBody MultipartFile file) {
        try {
            return ResponseEntity.ok(service.create(file, "registry-files"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
