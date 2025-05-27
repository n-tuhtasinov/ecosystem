package uz.technocorp.ecosystem.modules.prevention;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/preventions")
@RequiredArgsConstructor
public class PreventionController {

    private final PreventionService service;

    @GetMapping("/types")
    public ResponseEntity<ApiResponse> getTypeLis() {
        return ResponseEntity.ok(new ApiResponse(service.getTypes()));
    }

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody PreventionDto dto) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse("Profilaktika qilindi"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@CurrentUser User user, @Valid PreventionParamsDto dto) {
        return ResponseEntity.ok(new ApiResponse(service.getAll(user, dto)));
    }

    @GetMapping("/{preventionId}")
    public ResponseEntity<ApiResponse> getById(@CurrentUser User user, @PathVariable("preventionId") UUID preventionId) {
        PreventionView prevention = service.getById(user, preventionId);
        return ResponseEntity.ok(new ApiResponse(prevention));
    }

    @DeleteMapping("/{preventionId}")
    public ResponseEntity<ApiResponse> deleteById(@CurrentUser User user, @PathVariable("preventionId") UUID preventionId) {
        service.deleteById(user, preventionId);
        return ResponseEntity.ok(new ApiResponse("Profilaktika o'chirildi"));
    }
}
