package uz.technocorp.ecosystem.modules.department;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.department.dto.DepartmentDto;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DepartmentDto dto) {
        departmentService.create(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<?> update (@PathVariable Integer departmentId, @Valid @RequestBody DepartmentDto dto) {
        departmentService.update(departmentId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteById (@PathVariable Integer departmentId) {
        departmentService.delete(departmentId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping()
    public ResponseEntity<?> getAll (@RequestParam Map<String, String> params) {
        Page<Department> departments = departmentService.getAll(params);
        return ResponseEntity.ok(new ApiResponse(departments));
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAllBySelect () {
        List<Department> departments = departmentService.getAllBySelect();
        return ResponseEntity.ok(new ApiResponse(departments));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getById (@PathVariable Integer departmentId) {
        Department department = departmentService.getById(departmentId);
        return ResponseEntity.ok(new ApiResponse(department));
    }
}
