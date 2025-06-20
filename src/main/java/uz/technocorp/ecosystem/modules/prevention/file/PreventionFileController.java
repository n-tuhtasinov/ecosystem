package uz.technocorp.ecosystem.modules.prevention.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.prevention.file.dto.PathDto;
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
@RequestMapping("/api/v1/preventions/file")
@RequiredArgsConstructor
public class PreventionFileController {

    private final PreventionFileService service;

    @GetMapping("/{year}")
    public ResponseEntity<ApiResponse> get(@CurrentUser User user, @PathVariable Integer year) {
        return ResponseEntity.ok(new ApiResponse(service.get(user, year)));
    }

    // TODO Role check ( Regional )
    @PostMapping
    public ResponseEntity<ApiResponse> add(@CurrentUser User user, @RequestBody PathDto pathDto) {
        service.create(user, pathDto.getPath());
        return ResponseEntity.ok(new ApiResponse("Profilaktika fayli saqlandi"));
    }

    // TODO Role check ( Regional )
    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(@CurrentUser User user, @RequestParam("path") String path) {
        service.delete(user, path);
        return ResponseEntity.ok(new ApiResponse("Profilaktika fayli o'chirildi"));
    }
}
