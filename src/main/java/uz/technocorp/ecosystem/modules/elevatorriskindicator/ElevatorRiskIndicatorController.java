package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.RiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/elevator-risk-indicator")
@RequiredArgsConstructor
public class ElevatorRiskIndicatorController {

    private final ElevatorRiskIndicatorService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EquipmentRiskIndicatorDto dto) {
        service.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody EquipmentRiskIndicatorDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping("/{tin}/{id}")
    public ResponseEntity<?> getById(@PathVariable Long tin, @PathVariable UUID id) {
        List<RiskIndicatorView> allByIrsIdAndTin = service.findAllByEquipmentIdAndTin(id, tin);
        return ResponseEntity.ok(new ApiResponse(allByIrsIdAndTin));
    }

    @GetMapping("/{tin}")
    public ResponseEntity<?> getByTin(@PathVariable Long tin) {
        List<RiskIndicatorView> allByTin = service.findAllByTin(tin);
        return ResponseEntity.ok(new ApiResponse(allByTin));
    }
}
