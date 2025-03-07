package uz.technocorp.ecosystem.modules.office;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.department.dto.DepartmentDto;
import uz.technocorp.ecosystem.modules.office.dto.OfficeDto;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
public class OfficeController {
    private final OfficeService officeService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OfficeDto dto){
        officeService.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{officeId}")
    public ResponseEntity<?> update(@PathVariable Integer officeId, @Valid @RequestBody OfficeDto dto){
        officeService.update(officeId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("/{officeId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer officeId){
        officeService.deleteById(officeId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }



}
