package uz.technocorp.ecosystem.modules.appealdangerousobject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appealdangerousobject.dto.AppealDangerousObjectDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeal-dangerous_objects")
@RequiredArgsConstructor
public class AppealDangerousObjectController {

    private final AppealDangerousObjectService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody AppealDangerousObjectDto dto, BindingResult result) {
        service.create(user, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PatchMapping("/attachment")
    public ResponseEntity<?> setFile(@Valid @RequestPart("dto") AttachmentDto dto) throws IOException {
        service.setAttachments(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody AppealDangerousObjectDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

}
