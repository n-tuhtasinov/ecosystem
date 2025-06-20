package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealPdfService;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAcceptanceAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsTransferAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.03.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/irs")
@RequiredArgsConstructor
public class IrsAppealController {

    private final AppealService appealService;
    private final AppealPdfService appealPdfService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<IrsAppealDto> signedDto, HttpServletRequest request) {
        appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> createTransfer(@CurrentUser User user, @Valid @RequestBody IrsTransferAppealDto irsTransferDto) {
        appealService.create(irsTransferDto, user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/acceptance")
    public ResponseEntity<?> createAcceptance(@CurrentUser User user, @Valid @RequestBody IrsAcceptanceAppealDto irsAcceptanceDto) {
        appealService.create(irsAcceptanceDto, user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody IrsAppealDto irsDto) {
        String path = appealPdfService.preparePdfWithParam(irsDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    // inpektor tomonidan arizadagi kamchilik fayllarni yuklab arizani davom ettirib ketishi uchun
    //TODO: Faqat inspektor kirishi kerishi kerakligiga tekshirish kerak
//    @PutMapping("/{appealId}")
//    public ResponseEntity<?> update(@PathVariable UUID appealId, @Valid @RequestBody HfAppealDto hfDto) {
//        appealService.update(appealId, hfDto);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
//    }

}
