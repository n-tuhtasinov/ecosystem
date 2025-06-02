package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;

import java.util.Map;

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

//    @GetMapping
//    public ResponseEntity<?> getAll(@CurrentUser User user, @RequestParam Map<String, String> params) {
//        equipmentService.getAll(user, params);
//        return ResponseEntity.ok(new ApiResponse())
//    }


}
