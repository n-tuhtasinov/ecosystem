package uz.technocorp.ecosystem.modules.checklist;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.checklist.dto.ChecklistDto;
import uz.technocorp.ecosystem.modules.checklist.view.ChecklistView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService service;

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @RequestBody ChecklistDto dto) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @CurrentUser User user, @RequestBody ChecklistDto dto) {
        service.update(id, user, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
    }

    @GetMapping("/for-inspector")
    public ResponseEntity<?> getAllForInspector( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                 @RequestParam(value = "tin") Long tin) {
        Page<ChecklistView> checklists = service.getChecklists(page, size, tin);
        return ResponseEntity.ok(new ApiResponse(checklists));
    }

    @GetMapping
    public ResponseEntity<?> getAllForInspector( @CurrentUser User user,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        Page<ChecklistView> checklists = service.getChecklists(user, page, size);
        return ResponseEntity.ok(new ApiResponse(checklists));
    }
}
