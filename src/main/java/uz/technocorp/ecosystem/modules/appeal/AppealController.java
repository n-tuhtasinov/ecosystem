package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.ReplyDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals")
@RequiredArgsConstructor
public class AppealController {

    private final AppealService service;

    @PatchMapping("/set-inspector")
    public ResponseEntity<?> setInspector(@Valid @RequestBody SetInspectorDto dto) {
        service.setInspector(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> changeAppealStatus(@Valid @RequestBody AppealStatusDto dto) {
        service.changeAppealStatus(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @GetMapping
    public ResponseEntity<?> getAllAppeals(@RequestParam Map<String, String> params) {
        Page<AppealCustom> appeals = service.getAppealCustoms(params);
        return ResponseEntity.ok(new ApiResponse(appeals));
    }

    // @PreAuthorize("hasRole('INSPECTOR')")
    @GetMapping("/period")
    public ResponseEntity<?> getAllByPeriodAndInspector(@CurrentUser User user, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<AppealViewByPeriod> list = service.getAllByPeriodAndInspector(user, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @GetMapping("/{appealId}")
    public ResponseEntity<?> getById(@PathVariable UUID appealId) {
        AppealViewById byId = service.getById(appealId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }

    @PostMapping("/reply/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody ReplyDto replyDto) {
        String path = service.prepareReplyPdfWithParam(user, replyDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }
}
