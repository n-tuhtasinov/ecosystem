package uz.technocorp.ecosystem.modules.irsriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
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
@RequestMapping("/api/v1/irs-risk-indicators")
@RequiredArgsConstructor
public class IrsRiskIndicatorController {

    private final IrsRiskIndicatorService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<IrsRiskIndicatorDto> dtoList) {
        service.create(dtoList);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody IrsRiskIndicatorDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> attachFile(@PathVariable UUID id, @RequestBody FilePathDto dto) {
        service.attachFile(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable UUID id) {
        service.cancelRiskIndicator(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping("/for-one")
    public ResponseEntity<?> getById(@RequestParam(value = "tin") Long tin,
                                     @RequestParam(value = "id") UUID id,
                                     @RequestParam(value = "intervalId") Integer intervalId) {
        List<RiskIndicatorView> allByIrsIdAndTin = service.findAllByIrsIdAndTin(id, tin, intervalId);
        return ResponseEntity.ok(new ApiResponse(allByIrsIdAndTin));
    }

    @GetMapping
    public ResponseEntity<?> getByTin(@RequestParam(value = "tin") Long tin,
                                      @RequestParam(value = "intervalId") Integer intervalId) {
        List<RiskIndicatorView> allByTin = service.findAllByTin(tin, intervalId);
        return ResponseEntity.ok(new ApiResponse(allByTin));
    }

    @GetMapping("/to-fix")
    public ResponseEntity<?> getAllToFixByTin(@RequestParam(value = "tin") Long tin,
                                              @RequestParam(value = "intervalId") Integer intervalId,
                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        Page<RiskIndicatorView> allByTin = service.findAllToFixByTin(tin, intervalId, page, size);
        return ResponseEntity.ok(new ApiResponse(allByTin));
    }
}
