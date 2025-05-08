package uz.technocorp.ecosystem.modules.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/appealId")
    public ResponseEntity<?> getAllByAppealId(UUID appealId) {
        return ResponseEntity.ok(service.findByAppealId(appealId));
    }
}
