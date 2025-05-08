package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAcceptanceAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsTransferAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

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

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody IrsAppealDto irsDto) {
        appealService.create(irsDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> createTransfer(@CurrentUser User user, @Valid @RequestBody IrsTransferAppealDto irsTransferDto) {
        appealService.create(irsTransferDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/acceptance")
    public ResponseEntity<?> createAcceptance(@CurrentUser User user, @Valid @RequestBody IrsAcceptanceAppealDto irsAcceptanceDto) {
        appealService.create(irsAcceptanceDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    // inpektor tomonidan arizadagi kamchilik fayllarni yuklab arizani davom ettirib ketishi uchun
    //TODO: Faqat inspektor kirishi kerishi kerakligiga tekshirish kerak
//    @PutMapping("/{appealId}")
//    public ResponseEntity<?> update(@PathVariable UUID appealId, @Valid @RequestBody HfAppealDto hfDto) {
//        appealService.update(appealId, hfDto);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
//    }

}
