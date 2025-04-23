package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.irs.IrsAcceptanceAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.irs.IrsAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.irs.IrsTransferAppealDto;
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
    public ResponseEntity<?> createIrsAppeal(@CurrentUser User user, @Valid @RequestBody IrsAppealDto irsDto) {
        appealService.create(irsDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> createIrsTransferAppeal(@CurrentUser User user, @Valid @RequestBody IrsTransferAppealDto irsTransferDto) {
        appealService.create(irsTransferDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/acceptance")
    public ResponseEntity<?> createIrsAcceptanceAppeal(@CurrentUser User user, @Valid @RequestBody IrsAcceptanceAppealDto irsAcceptanceDto) {
        appealService.create(irsAcceptanceDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }
}
