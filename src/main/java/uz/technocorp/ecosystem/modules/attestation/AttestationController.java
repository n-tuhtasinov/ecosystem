package uz.technocorp.ecosystem.modules.attestation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealPdfService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
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
    private final AppealPdfService appealPdfService;

    //TODO   ROLE.LEGAL
    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody AttestationDto attestationDto) {
        String path = appealPdfService.preparePdfWithParam(attestationDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    //TODO   ROLE.LEGAL
    @PostMapping
    public ResponseEntity<ApiResponse> create(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<AttestationDto> signedDto, HttpServletRequest request) {
        service.create(user, signedDto, request);
        return ResponseEntity.ok().body(new ApiResponse("Ariza yaratildi"));
    }
}
