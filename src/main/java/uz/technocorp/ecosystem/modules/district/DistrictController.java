package uz.technocorp.ecosystem.modules.district;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.district.dto.DistrictDto;
import uz.technocorp.ecosystem.modules.district.projection.DistrictView;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewById;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody DistrictDto dto) {
        districtService.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("{districtId}")
    public ResponseEntity<?> update (@PathVariable Integer districtId, @Valid @RequestBody DistrictDto dto) {
        districtService.update(districtId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("{districtId}")
    public ResponseEntity<?> deleteById (@PathVariable Integer districtId) {
        districtService.deleteById(districtId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping
    public ResponseEntity<?> getAll (@RequestParam Map<String, String> params) {
        Page<DistrictView> districts = districtService.getAll(params);
        return ResponseEntity.ok(new ApiResponse(districts));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAllBySelect (@RequestParam(required = false) Integer regionId) {
        List<DistrictViewBySelect> districts = districtService.getAllBySelect(regionId);
        return ResponseEntity.ok(new ApiResponse(districts));
    }

    @GetMapping("{districtId}")
    public ResponseEntity<?> getById (@PathVariable Integer districtId) {
        DistrictViewById byId = districtService.getById(districtId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }
}
