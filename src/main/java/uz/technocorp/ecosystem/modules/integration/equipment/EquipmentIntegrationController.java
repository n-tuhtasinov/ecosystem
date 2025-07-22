package uz.technocorp.ecosystem.modules.integration.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;

/**
 * @author Suxrob
 * @version 1.0
 * @created 21.07.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/integration")
@RequiredArgsConstructor
@PreAuthorize("hasRole('INTEGRATOR')")
public class EquipmentIntegrationController {

    private final EquipmentIntegrationService service;

    @GetMapping("/cranes/{tinOrPin}")
    public ResponseEntity<InfoDto<EquipmentInfoDto>> getCranesByTinOrPin(@PathVariable String tinOrPin) {
        return ResponseEntity.ok(service.getEquipmentInfo(tinOrPin, EquipmentType.CRANE));
    }

    @GetMapping("/elevators/{tinOrPin}")
    public ResponseEntity<InfoDto<EquipmentInfoDto>> getElevatorsByTinOrPin(@PathVariable String tinOrPin) {
        return ResponseEntity.ok(service.getEquipmentInfo(tinOrPin, EquipmentType.ELEVATOR));
    }
}
