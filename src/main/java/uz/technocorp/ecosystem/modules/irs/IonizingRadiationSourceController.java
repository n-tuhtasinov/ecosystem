package uz.technocorp.ecosystem.modules.irs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 01.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/irs")
@RequiredArgsConstructor
public class IonizingRadiationSourceController {

    private final IonizingRadiationSourceService service;

//    @PostMapping("/without-appeal")
//    public ResponseEntity<?> create(@RequestBody IrsDto dto) {
//        service.create(dto);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody IrsDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }
}
