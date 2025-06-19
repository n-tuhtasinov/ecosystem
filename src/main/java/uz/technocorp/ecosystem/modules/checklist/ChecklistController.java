package uz.technocorp.ecosystem.modules.checklist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.checklist.dto.ChecklistDto;
import uz.technocorp.ecosystem.modules.checklist.view.ChecklistView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/checklists")
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

    @GetMapping
    public ResponseEntity<?> getAllByObjectId(@RequestParam(value = "intervalId") Integer intervalId,
                                              @RequestParam(value = "objectId") UUID objectId,
                                              @RequestParam(value = "tin") Long tin) {
        List<ChecklistView> checklists = service.getChecklists(tin, objectId, intervalId);
        return ResponseEntity.ok(new ApiResponse(checklists));
    }
}
