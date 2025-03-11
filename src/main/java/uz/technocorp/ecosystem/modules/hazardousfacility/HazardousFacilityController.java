package uz.technocorp.ecosystem.modules.hazardousfacility;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.03.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/hazardous-facilities")
@RequiredArgsConstructor
public class HazardousFacilityController {

    private final HazardousFacilityService service;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable UUID id) {
        service.create(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }
}
