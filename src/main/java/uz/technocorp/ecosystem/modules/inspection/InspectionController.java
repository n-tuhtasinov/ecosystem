package uz.technocorp.ecosystem.modules.inspection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionFullDto;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionShortInfo;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 31.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService service;

    @PutMapping("/set-inspector/{inspectionId}")
    public ResponseEntity<?> setInspectors(@PathVariable UUID inspectionId, @RequestBody InspectionDto dto) {
        service.update(inspectionId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PutMapping("/set-files/{inspectionId}")
    public ResponseEntity<?> setFiles(@PathVariable UUID inspectionId, @RequestBody InspectionUpdateDto dto) {
        service.update(inspectionId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @PatchMapping("/set-act/{inspectionId}")
    public ResponseEntity<?> update(@PathVariable UUID inspectionId) {
        service.updateStatus(inspectionId, InspectionStatus.CONDUCTED);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@CurrentUser User user,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "status") InspectionStatus status,
                                    @RequestParam(value = "tin", required = false) Long tin,
                                    @RequestParam(value = "intervalId") Integer intervalId) {
        Page<InspectionCustom> all = service.getAll(user, page, size, tin, status, intervalId);
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        InspectionFullDto byId = service.getById(id);
        return ResponseEntity.ok(new ApiResponse(byId));
    }

    @GetMapping("/period")
    public ResponseEntity<?> getAllByInspector(@CurrentUser User user,
                                    @RequestParam LocalDate startDate,
                                    @RequestParam LocalDate endDate) {
        List<InspectionShortInfo> all = service.getAllByInspector(user, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse(all));
    }
}

