package uz.technocorp.ecosystem.modules.prevention;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.util.ArrayList;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/prevention/inspector")
@RequiredArgsConstructor
public class InspectorPreventionController {

    private final PreventionService service;

    @GetMapping("/with-events")
    public ResponseEntity<ApiResponse> getAllOrganizationsWithEvents(@CurrentUser User user, @RequestBody PreventionParamsDto params) {
        service.getOrganizationsWithEvents(user, params);

        return ResponseEntity.ok(new ApiResponse(new ArrayList<>()));
    }

    @GetMapping("/without-events")
    public ResponseEntity<ApiResponse> getAllOrganizationsWithoutEvents(@CurrentUser User user, @RequestBody PreventionParamsDto params) {
        service.getOrganizationsWithoutEvents(user, params);

        return ResponseEntity.ok(new ApiResponse(new ArrayList<>()));
    }

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody PreventionDto dto) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse("Profilaktika ishlari olib borildi"));
    }
}
