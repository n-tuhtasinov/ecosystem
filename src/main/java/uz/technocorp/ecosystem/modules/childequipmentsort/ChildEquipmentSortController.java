package uz.technocorp.ecosystem.modules.childequipmentsort;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.childequipmentsort.dto.ChildEquipmentSortDto;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortView;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewById;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/child-equipment-sorts")
@RequiredArgsConstructor
public class ChildEquipmentSortController {

    private final ChildEquipmentSortService childEquipmentSortService;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody ChildEquipmentSortDto childEquipmentSortDto) {
        childEquipmentSortService.create(childEquipmentSortDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @GetMapping
    public ResponseEntity<?> getAllByPage (@RequestParam Map<String, String> params, @RequestParam(required = false) Integer childEquipmentId) {
        Page<ChildEquipmentSortView> page = childEquipmentSortService.getAllByPage(params, childEquipmentId);
        return ResponseEntity.ok(new ApiResponse(page));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAll (@RequestParam Integer childEquipmentId) {
        List<ChildEquipmentSortViewBySelect> list = childEquipmentSortService.getAll(childEquipmentId);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @PutMapping("/{childEquipmentSortId}")
    public ResponseEntity<?> update (@PathVariable Integer childEquipmentSortId, @RequestBody ChildEquipmentSortDto childEquipmentSortDto) {
        childEquipmentSortService.update(childEquipmentSortId, childEquipmentSortDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping("/{childEquipmentSortId}")
    public ResponseEntity<?> getById (@PathVariable Integer childEquipmentSortId) {
        ChildEquipmentSortViewById byId = childEquipmentSortService.getById(childEquipmentSortId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }
}
