package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;

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

    @PatchMapping
    public ResponseEntity<?> setInspector(@Valid @RequestBody SetInspectorDto dto) {
        service.setInspector(dto.inspector_id(), dto.appeal_id());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }


}
