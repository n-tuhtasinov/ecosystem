package uz.technocorp.ecosystem.modules.hfappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfDeregisterAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfModificationAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/hf")
@RequiredArgsConstructor
public class HfAppealController {

    private final AppealService appealService;
    private final AppealPdfService appealPdfService;

    @PostMapping
    public ResponseEntity<ApiResponse> createAndSign(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<HfAppealDto> signedDto, HttpServletRequest request) {
        appealService.setHfTypeName(signedDto.getDto());
        appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    // inpektor tomonidan arizadagi kamchilik fayllarni yuklab arizani davom ettirib ketishi uchun
    //TODO: Faqat inspektor kirishi kerishi kerakligiga tekshirish kerak
    @PutMapping("/{appealId}")
    public ResponseEntity<?> updateHfAppeal(@PathVariable UUID appealId, @Valid @RequestBody HfAppealDto hfDto) {
        appealService.update(appealId, hfDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PostMapping("/deregister")
    public ResponseEntity<?> createHfDeregisterAppeal(@CurrentUser User user, @Valid @RequestBody HfDeregisterAppealDto hfDeregisterAppealDto) {
        appealService.create(hfDeregisterAppealDto, user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/modification")
    public ResponseEntity<?> createHfModificationAppeal(@CurrentUser User user, @Valid @RequestBody HfModificationAppealDto hfModificationAppealDto) {
        appealService.create(hfModificationAppealDto, user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody HfAppealDto hfDto) {
        String path = appealPdfService.preparePdfWithParam(hfDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }
}
