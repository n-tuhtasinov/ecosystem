package uz.technocorp.ecosystem.modules.hftype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;
import uz.technocorp.ecosystem.modules.hftype.dto.HazardousFacilityTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/hf-types")
@RequiredArgsConstructor
@Slf4j
public class HazardousFacilityTypeController {

    private final HazardousFacilityTypeService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HazardousFacilityTypeDto dto) {
        try {
            service.create(dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Ushbu XICHO turi tizimda oldindan mavjud!");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HazardousFacilityTypeDto dto) {
        try {
            service.update(id, dto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.DELETED));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            HazardousFacilityType byId = service.getById(id);
            return ResponseEntity.ok(new ApiResponse(byId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllByPage(@RequestParam(value = "search", defaultValue = "") String search,
                                          @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        try {
            Page<HazardousFacilityType> allPage = service.getAllPage(page, size, search);
            return ResponseEntity.ok(new ApiResponse(allPage));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> getAll(@RequestParam(value = "search", defaultValue = "") String search) {
        try {
            List<HazardousFacilityType> all = service.getAll(search);
            return ResponseEntity.ok(new ApiResponse(all));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ResponseMessage.CONFLICT));
        }
    }
}
