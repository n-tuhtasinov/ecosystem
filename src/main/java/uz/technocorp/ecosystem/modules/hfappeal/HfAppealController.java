package uz.technocorp.ecosystem.modules.hfappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfDeregisterAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfModificationAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.SignedHfAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

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

    @PostMapping
    public ResponseEntity<ApiResponse> createAndSign(@CurrentUser User user, @Valid @RequestBody SignedHfAppealDto signedDto, HttpServletRequest request) {
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
        appealService.generatePdfWithParam(hfDto, user);
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "hf-appeal" + ".pdf");*/

        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", "/files/registry-files/2025/may/8/1746686108933.pdf"));
    }
}
