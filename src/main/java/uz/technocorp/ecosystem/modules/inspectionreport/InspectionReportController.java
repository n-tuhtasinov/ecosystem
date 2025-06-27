package uz.technocorp.ecosystem.modules.inspectionreport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 31.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/inspection-reports")
@RequiredArgsConstructor
public class InspectionReportController {

    private final InspectionReportService service;

    @PostMapping("/{inspectionId}")
    public ResponseEntity<?> create(@PathVariable UUID inspectionId, @RequestBody InspectionReportDto dto) {
        service.create(inspectionId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PatchMapping("/{inspectionId}")
    public ResponseEntity<?> update(@PathVariable UUID inspectionId, @CurrentUser User user, @RequestBody InspectionReportDto dto) {
        service.update(user, inspectionId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@CurrentUser User user,
                                    @RequestParam(value = "inspectionId") UUID inspectionId,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "eliminated") Boolean eliminated) {
        Page<InspectionReportView> allByInspectionId = service.getAllByInspectionId(user, inspectionId, page, size, eliminated);
        return ResponseEntity.ok(new ApiResponse(allByInspectionId));
    }
}

