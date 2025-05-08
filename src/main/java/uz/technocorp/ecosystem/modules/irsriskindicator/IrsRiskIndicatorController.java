package uz.technocorp.ecosystem.modules.irsriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.irsriskindicator.dto.IrsRiskIndicatorDto;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/irs-risk-indicator")
@RequiredArgsConstructor
public class IrsRiskIndicatorController {

    private final IrsRiskIndicatorService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody IrsRiskIndicatorDto dto) {
        service.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody IrsRiskIndicatorDto dto) {
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
        List<RiskIndicatorView> allByIrsIdAndTin = service.findAllByIrsIdAndTin(id, tin);
        return ResponseEntity.ok(new ApiResponse(allByIrsIdAndTin));
    }

    @GetMapping("/{tin}")
    public ResponseEntity<?> getByTin(@PathVariable Long tin) {
        List<RiskIndicatorView> allByTin = service.findAllByTin(tin);
        return ResponseEntity.ok(new ApiResponse(allByTin));
    }
}
