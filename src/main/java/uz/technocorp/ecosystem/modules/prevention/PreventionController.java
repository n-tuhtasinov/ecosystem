package uz.technocorp.ecosystem.modules.prevention;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/preventions")
@RequiredArgsConstructor
public class PreventionController {

    private final PreventionService service;

    @GetMapping("/types")
    public ResponseEntity<ApiResponse> getTypeLis() {
        return ResponseEntity.ok(new ApiResponse(service.getTypes()));
    }

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody PreventionDto dto) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse("Profilaktika ishlari olib borildi"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@CurrentUser User user,
                                              @RequestParam(value = "isPassed", required = false, defaultValue = "false") Boolean isPassed,
                                              @RequestParam(value = "search", required = false) String search,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "12") Integer size,
                                              @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                              @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                              @RequestParam(value = "viewed", required = false) Boolean viewed,
                                              @RequestParam(value = "officeId", required = false) Integer officeId,
                                              @RequestParam(value = "inspectorId", required = false) UUID inspectorId) {
        Page<?> paging = service.getAll(user, new PreventionParamsDto(isPassed, search, page, size, startDate, endDate, viewed, officeId, inspectorId));
        return ResponseEntity.ok(new ApiResponse(paging));
    }

    @GetMapping("/{preventionId}")
    public ResponseEntity<ApiResponse> getById(@CurrentUser User user, @PathVariable("preventionId") UUID preventionId) {
        PreventionView prevention = service.getById(user, preventionId);
        return ResponseEntity.ok(new ApiResponse(prevention));
    }

    @DeleteMapping("/{preventionId}")
    public ResponseEntity<ApiResponse> deleteById(@CurrentUser User user, @PathVariable("preventionId") UUID preventionId) {
        service.deleteById(user, preventionId);
        return ResponseEntity.ok(new ApiResponse("Profilaktika o'chirildi"));
    }
}
