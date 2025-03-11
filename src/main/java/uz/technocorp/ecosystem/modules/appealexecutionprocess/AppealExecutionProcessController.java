package uz.technocorp.ecosystem.modules.appealexecutionprocess;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.dto.AppealExecutionProcessDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeal-execution-processes")
@RequiredArgsConstructor
public class AppealExecutionProcessController {

    private final AppealExecutionProcessService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AppealExecutionProcessDto dto) {
        try {
            service.writeExecutionProcess(dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{appealId}")
    public ResponseEntity<?> get(@PathVariable UUID appealId) {
        return ResponseEntity.ok(service.getAll(appealId));
    }
}
