package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;

import java.util.Map;

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

}
