package uz.technocorp.ecosystem.modules.declarationappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.CadastrePassportAppealService;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.ConfirmPassportDto;
import uz.technocorp.ecosystem.modules.declarationappeal.dto.DeclarationDto;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.RejectPassportDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/declaration")
@RequiredArgsConstructor
public class DeclarationAppealController {

    private final AppealService appealService;
    private final AppealPdfService appealPdfService;
    private final DeclarationAppealService declarationAppealService;


    @PostMapping("/generate-pdf")
    public ResponseEntity<?> generateDeclarationPdf(@CurrentUser User user, @Valid @RequestBody DeclarationDto declarationDto) {
        String path = appealPdfService.preparePdfWithParam(declarationDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDeclaration(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<DeclarationDto> signedDto, HttpServletRequest request) {
        appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/confirmation/generate-pdf")
    public ResponseEntity<ApiResponse> generateConfirmationPdf(@CurrentUser User user, @Valid @RequestBody ConfirmPassportDto confirmPassportDto) {
        String path = declarationAppealService.generateConfirmationPdf(user, confirmPassportDto);
        return ResponseEntity.ok(new ApiResponse("Pdf fayl yaratildi", path));
    }

    @PostMapping("/rejection/generate-pdf")
    public ResponseEntity<ApiResponse> generateRejectionPdf(@CurrentUser User user, @Valid @RequestBody RejectPassportDto rejectPassportDto) {
        String path = declarationAppealService.generateRejectionPdf(user, rejectPassportDto);
        return ResponseEntity.ok(new ApiResponse("Pdf fayl yaratildi", path));
    }

    @PostMapping("/confirmation")
    public ResponseEntity<ApiResponse> confirm(@CurrentUser User user, @Valid @RequestBody SignedReplyDto<ConfirmPassportDto> replyDto, HttpServletRequest request ) {
        declarationAppealService.confirm(user, replyDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CONFIRMED));
    }

    @PostMapping("/rejection")
    public ResponseEntity<ApiResponse> reject(@CurrentUser User user, @Valid @RequestBody SignedReplyDto<RejectPassportDto> replyDto, HttpServletRequest request) {
        declarationAppealService.reject(user, replyDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.REJECTED));
    }

}
