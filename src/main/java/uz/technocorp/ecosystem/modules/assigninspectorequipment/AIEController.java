package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AIDto;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/assign-inspector-equipments")
@RequiredArgsConstructor
public class AIEController {

    private final AIEService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AIDto dto) {
        service.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody AIDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInspector(@PathVariable UUID id) {
        AssignInspectorInfo assignInfo = service.getInspectorInfo(id);
        return ResponseEntity.ok(new ApiResponse(assignInfo));
    }
}
