package uz.technocorp.ecosystem.modules.hfappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfDeregisterAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfModificationAppealDto;
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
    public ResponseEntity<?> createHfAppeal(@CurrentUser User user, @Valid @RequestBody HfAppealDto hfDto) {
        appealService.create(hfDto, user);
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
    public ResponseEntity<byte[]> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody HfAppealDto hfDto) {
        byte[] pdfBytes = appealService.generatePdfWithParam(hfDto, user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "hf-appeal" + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
