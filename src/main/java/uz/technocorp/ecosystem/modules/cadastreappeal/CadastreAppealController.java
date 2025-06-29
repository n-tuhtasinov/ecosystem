package uz.technocorp.ecosystem.modules.cadastreappeal;

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
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.CadastrePassportDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.ConfirmCadastreDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.DeclarationDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.RejectCadastreDto;
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
@RequestMapping("/api/v1/appeals/cadastre")
@RequiredArgsConstructor
public class CadastreAppealController {

    private final AppealService appealService;
    private final AppealPdfService appealPdfService;
    private final CadastreAppealService cadastreAppealService;

    @PostMapping("/passport/generate-pdf")
    public ResponseEntity<?> generatePassportPdf(@CurrentUser User user, @Valid @RequestBody CadastrePassportDto cadastrePassportDto) {
        String path = appealPdfService.preparePdfWithParam(cadastrePassportDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/passport")
    public ResponseEntity<ApiResponse> createPassport(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CadastrePassportDto> signedDto, HttpServletRequest request) {
        appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/declaration/generate-pdf")
    public ResponseEntity<?> generateDeclarationPdf(@CurrentUser User user, @Valid @RequestBody DeclarationDto declarationDto) {
        String path = appealPdfService.preparePdfWithParam(declarationDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/declaration")
    public ResponseEntity<ApiResponse> createDeclaration(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<DeclarationDto> signedDto, HttpServletRequest request) {
        appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/confirmation/generate-pdf")
    public ResponseEntity<ApiResponse> generateConfirmationPdf(@CurrentUser User user, @Valid @RequestBody ConfirmCadastreDto confirmCadastreDto) {
        String path = cadastreAppealService.generateConfirmationPdf(user, confirmCadastreDto);
        return ResponseEntity.ok(new ApiResponse("Pdf fayl yaratildi", path));
    }

    @PostMapping("/rejection/generate-pdf")
    public ResponseEntity<ApiResponse> generateRejectionPdf(@CurrentUser User user, @Valid @RequestBody RejectCadastreDto rejectCadastreDto) {
        String path = cadastreAppealService.generateRejectionPdf(user, rejectCadastreDto);
        return ResponseEntity.ok(new ApiResponse("Pdf fayl yaratildi", path));
    }

}
