package uz.technocorp.ecosystem.modules.region;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.region.dto.RegionDto;
import uz.technocorp.ecosystem.modules.region.projection.RegionView;
import uz.technocorp.ecosystem.modules.region.projection.RegionViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody RegionDto dto) {
        regionService.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("{regionId}")
    public ResponseEntity<?> update (@PathVariable Integer regionId, @Valid @RequestBody RegionDto dto) {
        regionService.update(regionId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("{regionId}")
    public ResponseEntity<?> deleteById (@PathVariable Integer regionId) {
        regionService.deleteById(regionId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping
    public ResponseEntity<?> getAll (@RequestParam Map<String, String> params) {
        Page<RegionView> regions = regionService.getAll(params);
        return ResponseEntity.ok(new ApiResponse(regions));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAllBySelect () {
        List<RegionViewBySelect> regions = regionService.getAllBySelect();
        return ResponseEntity.ok(new ApiResponse(regions));
    }
}
