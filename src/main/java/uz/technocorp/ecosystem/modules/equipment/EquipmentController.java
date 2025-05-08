package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.shared.ApiResponse;
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


}
