package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/accreditations")
@RequiredArgsConstructor
public class AccreditationController {

    private final AccreditationService accreditationService;
    private final AppealPdfService appealPdfService;

    @PostMapping("/reject/generate-pdf")
    public ResponseEntity<ApiResponse> generateRejectionPdf(@CurrentUser User user, @Valid @RequestBody AccreditationRejectionDto dto) {
        String path = appealPdfService.prepareAccreditationPdfWithParam(user, dto, true);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/reject")
    public ResponseEntity<ApiResponse> createRejectionLetter(@CurrentUser User user, @Valid @RequestBody SignedReplyDto<AccreditationRejectionDto> dto, HttpServletRequest request) {
        accreditationService.notConfirmed(user, dto, request, true);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/cancel/generate-pdf")
    public ResponseEntity<ApiResponse> generateCanceledPdfFromAccreditation(@CurrentUser User user, @Valid @RequestBody AccreditationRejectionDto dto) {
        String path = appealPdfService.prepareAccreditationPdfWithParam(user, dto, true);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelledAppeal(@CurrentUser User user, @Valid @RequestBody SignedReplyDto<AccreditationRejectionDto> dto, HttpServletRequest request) {
        accreditationService.notConfirmed(user, dto, request, false);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }
    @PostMapping("/certificate/generate-pdf")
    public ResponseEntity<ApiResponse> generateCertificatePdfFromAccreditation(@CurrentUser User user, @Valid @RequestBody AccreditationDto dto) {
        String path = accreditationService.generateCertificate(user, dto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/certificate")
    public ResponseEntity<ApiResponse> createAccreditation(@CurrentUser User user, @Valid @RequestBody SignedReplyDto<AccreditationDto> dto, HttpServletRequest request) {
        accreditationService.createAccreditation(user, dto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

}
