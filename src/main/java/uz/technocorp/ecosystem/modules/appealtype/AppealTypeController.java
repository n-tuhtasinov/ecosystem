package uz.technocorp.ecosystem.modules.appealtype;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.publics.dto.SimpleDto;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeal-types")
@RequiredArgsConstructor
public class AppealTypeController {

    private final AppealTypeService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SimpleDto dto) {
        try {
            service.create(dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SimpleDto dto) {
        try {
            service.update(id, dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllByPage(@RequestParam(value = "search", defaultValue = "") String search,
                                          @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        try {
            return ResponseEntity.ok(service.getAllPage(page, size, search));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAll(@RequestParam(value = "search", defaultValue = "") String search) {
        try {
            return ResponseEntity.ok(service.getAll(search));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }
}
