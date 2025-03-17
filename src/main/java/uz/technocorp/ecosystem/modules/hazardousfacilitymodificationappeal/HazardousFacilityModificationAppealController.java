package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal.dto.HazardousFacilityModificationAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hazardous-facility-modification-appeals")
public class HazardousFacilityModificationAppealController {

    private final HazardousFacilityModificationAppealService service;

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody HazardousFacilityModificationAppealDto dto) {
        try {
            service.create(user, dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody HazardousFacilityModificationAppealDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }
}
