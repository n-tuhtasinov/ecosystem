package uz.technocorp.ecosystem.modules.attestation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationConductDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationParamsDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/attestation")
@RequiredArgsConstructor
public class AttestationController {

    private final AttestationService service;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@CurrentUser User user, @Valid AttestationParamsDto dto) {
        return ResponseEntity.ok(new ApiResponse(service.getAllByParams(user, dto)));
    }

    @GetMapping("/{attestationId}")
    public ResponseEntity<ApiResponse> get(@CurrentUser User user, @PathVariable UUID attestationId) {
        return ResponseEntity.ok().body(new ApiResponse(service.getById(user, attestationId)));
    }

    @GetMapping("/by-appeal/{appealId}")
    public ResponseEntity<ApiResponse> getByAppeal(@CurrentUser User user, @PathVariable UUID appealId) {
        return ResponseEntity.ok().body(new ApiResponse(service.getByAppeal(user, appealId)));
    }

    // TODO ROLE -> REGIONAL - COMMITTEE
    @PostMapping("/conduct")
    public ResponseEntity<ApiResponse> conduct(@CurrentUser User user, @Valid @RequestBody AttestationConductDto conductDto) {
        service.conduct(user, conductDto);
        return ResponseEntity.ok().body(new ApiResponse("Muvaffaqiyatli amalga oshirildi"));
    }

}
