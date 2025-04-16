package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

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

    @PostMapping("/irs")
    public ResponseEntity<?> createIrsAppeal(@CurrentUser User user, @Valid @RequestBody IrsAppealDto irsDto) {
        service.create(irsDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/irs/transfer")
    public ResponseEntity<?> createIrsTransferAppeal(@CurrentUser User user, @Valid @RequestBody IrsTransferAppealDto irsTransferDto) {
        service.create(irsTransferDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/irs/acceptance")
    public ResponseEntity<?> createIrsAcceptanceAppeal(@CurrentUser User user, @Valid @RequestBody IrsAcceptanceAppealDto irsAcceptanceDto) {
        service.create(irsAcceptanceDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/hf")
    public ResponseEntity<?> createHfAppeal(@CurrentUser User user, @Valid @RequestBody HfAppealDto hfDto) {
        service.create(hfDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    // inpektor tomonidan arizadagi kamchilik fayllarni yuklab arizani davom ettirib ketishi uchun
    @PutMapping("/hf/{appealId}")
    public ResponseEntity<?> updateHfAppeal(@PathVariable UUID appealId, @CurrentUser User user, @Valid @RequestBody HfAppealDto hfDto) {
        service.update(appealId, hfDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PostMapping("/hf/deregister")
    public ResponseEntity<?> createHfDeregisterAppeal(@CurrentUser User user, @Valid @RequestBody HfDeregisterAppealDto hfDeregisterAppealDto) {
        service.create(hfDeregisterAppealDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/hf/modification")
    public ResponseEntity<?> createHfModificationAppeal(@CurrentUser User user, @Valid @RequestBody HfModificationAppealDto hfModificationAppealDto) {
        service.create(hfModificationAppealDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

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
        return ResponseEntity.ok(service.getAppealCustoms(params));
    }

}
