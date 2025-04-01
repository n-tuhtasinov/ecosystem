package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

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

    @PostMapping("/irs")
    public ResponseEntity<?> createIrsAppeal(@CurrentUser User user, @Valid @RequestBody IrsDto irsDto) {
        service.create(irsDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

//    @PostMapping("/hf")
//    public ResponseEntity<?> createHfAppeal(@CurrentUser User user, @Valid @RequestBody HfDto hfDto) {
//        service.create(hfDto,user);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
//    }

//    @PutMapping("/hf")
//    public ResponseEntity<?> modifyHfAppeal(@CurrentUser User user, @Valid @RequestBody HfDto hfDto) {
//        service.update(hfDto,user);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
//    }


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
