package uz.technocorp.ecosystem.modules.hf;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.hf.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfRegistryDto;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.03.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/hf")
@RequiredArgsConstructor
public class HazardousFacilityController {

    private final HazardousFacilityService service;

    @PostMapping
    public ResponseEntity<?> createByAppeal(@Valid @RequestBody HfRegistryDto hfRegistryDto) {
        service.create(hfRegistryDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/without-appeal")
    public ResponseEntity<?> create(@Valid @RequestBody HfDto dto) {
        service.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{hfId}")
    public ResponseEntity<?> update(@PathVariable UUID hfId, @Valid @RequestBody HfDto dto) {
        service.update(hfId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/deregister/{hfId}")
    public ResponseEntity<?> deregister(@PathVariable UUID hfId, @Valid @RequestBody HfDeregisterDto hfDeregisterDto) {
        try {
            service.deregister(hfId, hfDeregisterDto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/periodic-update/{hfId}")
    public ResponseEntity<?> periodicUpdate(@PathVariable UUID hfId, @Valid @RequestBody HfPeriodicUpdateDto hfPeriodicUpdateDto) {
        try {
            service.periodicUpdate(hfId, hfPeriodicUpdateDto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
