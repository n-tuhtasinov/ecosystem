package uz.technocorp.ecosystem.modules.prevention;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.prevention.dto.PagingDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/prevention/inspector")
@RequiredArgsConstructor
public class InspectorController {

    private final PreventionService service;

    @PostMapping("/with-events")
    public ResponseEntity<ApiResponse> getAllOrganizationsWithEvents(@CurrentUser User user, @RequestBody PreventionParamsDto params) {
        PagingDto<PreventionView> paging = service.getOrganizationsWithEventsByInspector(user, params);
        return ResponseEntity.ok(new ApiResponse(paging));
    }

    @PostMapping("/without-events")
    public ResponseEntity<ApiResponse> getAllOrganizationsWithoutEvents(@CurrentUser User user, @RequestBody PreventionParamsDto params) {
        PagingDto<ProfileView> paging = service.getOrganizationsWithoutEvents(user, params);
        return ResponseEntity.ok(new ApiResponse(paging));
    }

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody PreventionDto dto) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse("Profilaktika ishlari olib borildi"));
    }
}
