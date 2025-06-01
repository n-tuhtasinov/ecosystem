package uz.technocorp.ecosystem.modules.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @Operation(
            summary = "Tashkilot haqida ma'lumot olish",
            parameters = {
                    @Parameter(name = "tin", description = "Tashkilot STIR i", schema = @Schema(type = "number"))
            }
    )
    @GetMapping("/info")
    public ResponseEntity<?> getProfileInfo(@RequestParam(value = "tin") Long tin) {
        ProfileInfoView profileInfo = service.getProfileInfo(tin);
        return ResponseEntity.ok(new ApiResponse(profileInfo));
    }
}
