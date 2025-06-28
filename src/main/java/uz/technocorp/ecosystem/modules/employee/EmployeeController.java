package uz.technocorp.ecosystem.modules.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/positions")
    public ResponseEntity<ApiResponse> positions() {
        return ResponseEntity.ok(new ApiResponse(service.getEmployeeLevels()));
    }

    @GetMapping("/by-hf/{hfId}")
    public ResponseEntity<ApiResponse> positions(@PathVariable("hfId") UUID hfId) {
        return ResponseEntity.ok(new ApiResponse(service.getEmployeesByHf(hfId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> add(@CurrentUser User user, @RequestBody @Valid EmployeeListDto dto) {
        Integer addCount = service.add(user, dto);
        return ResponseEntity.ok(new ApiResponse(addCount + " ta xodim qo'shildi"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@CurrentUser User user, @RequestBody @Valid EmployeeListDto dto) {
        service.updateEmployees(user, dto);
        return ResponseEntity.ok(new ApiResponse("Ma'lumotlar o'zgartirildI"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(@CurrentUser User user, @RequestBody @Valid EmployeeDeleteDto dto) {
        Integer deleteCount = service.deleteByPinList(user, dto);
        return ResponseEntity.ok(new ApiResponse(deleteCount + " ta xodim o'chirildi"));
    }
}
