package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody EquipmentRegistryDto equipmentRegistryDto) {
        equipmentService.create(equipmentRegistryDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @GetMapping("/attractions/risk-assessment")
    public ResponseEntity<?> getAllAttractionsForRiskAssessment(@CurrentUser User user,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                     @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                     @RequestParam(value = "intervalId") Integer intervalId,
                                                     @RequestParam(value = "isAssigned") Boolean isAssigned
    ) {
        Page<HfPageView> all = equipmentService.getAllAttractionForRiskAssessment(user, page, size, legalTin, registryNumber, isAssigned, intervalId);
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/elevators/risk-assessment")
    public ResponseEntity<?> getAllElevatorsForRiskAssessment(@CurrentUser User user,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                     @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                     @RequestParam(value = "intervalId") Integer intervalId,
                                                     @RequestParam(value = "isAssigned") Boolean isAssigned
    ) {
        Page<HfPageView> all = equipmentService.getAllElevatorForRiskAssessment(user, page, size, legalTin, registryNumber, isAssigned, intervalId);
        return ResponseEntity.ok(new ApiResponse(all));
    }
}
