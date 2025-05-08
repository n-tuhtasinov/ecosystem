package uz.technocorp.ecosystem.modules.childequipment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.childequipment.dto.ChildEquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/child-equipments")
@RequiredArgsConstructor
public class ChildEquipmentController {

    private final ChildEquipmentService childEquipmentService;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody ChildEquipmentDto childEquipmentDto) {
        childEquipmentService.create(childEquipmentDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @GetMapping
    public ResponseEntity<?> getAll (@RequestParam Map<String, String> params, @RequestParam(required = false) EquipmentType equipmentType) {
        Page<ChildEquipment> page = childEquipmentService.getAll(params, equipmentType);
        return ResponseEntity.ok(new ApiResponse(page));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getSelect (@RequestParam EquipmentType equipmentType) {
        List<ChildEquipment> list = childEquipmentService.getSelect(equipmentType);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @PutMapping("/{childEquipmentId}")
    public ResponseEntity<?> update (@PathVariable Integer childEquipmentId, @RequestBody ChildEquipmentDto childEquipmentDto) {
        childEquipmentService.update(childEquipmentId, childEquipmentDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping("/{childEquipmentId}")
    public ResponseEntity<?> getById (@PathVariable Integer childEquipmentId) {
        ChildEquipment child = childEquipmentService.getById(childEquipmentId);
        return ResponseEntity.ok(new ApiResponse(child));
    }

}
