package uz.technocorp.ecosystem.modules.attestation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

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

    @PostMapping
    public ResponseEntity<ApiResponse> create(@CurrentUser User user, @RequestBody @Valid AttestationDto attestationDto) {
        service.create(user, attestationDto);
        return ResponseEntity.ok().body(new ApiResponse("Ariza yaratildi"));
    }
}
