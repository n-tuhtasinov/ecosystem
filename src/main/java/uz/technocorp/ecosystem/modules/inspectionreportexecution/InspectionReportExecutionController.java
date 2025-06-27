package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.dto.IRExecutionDto;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.view.InspectionReportExecutionView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
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
@RequestMapping("/api/v1/inspection-report-executions")
@RequiredArgsConstructor
public class InspectionReportExecutionController {
    private final InspectionReportExecutionService service;

    @PostMapping("/{reportId}")
    public ResponseEntity<?> create(@PathVariable UUID reportId, @RequestBody IRExecutionDto dto) {
        service.create(reportId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<?> reject(@CurrentUser User user, @PathVariable UUID reportId, @RequestBody IRExecutionDto dto) {
        service.reject(user, reportId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity<?> accept(@CurrentUser User user, @PathVariable UUID reportId) {
        service.accept(user, reportId);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<?> getAllByReportId(@PathVariable UUID reportId) {
        List<InspectionReportExecutionView> executionViews = service.getAllByInspectionReportId(reportId);
        return ResponseEntity.ok(new ApiResponse(executionViews));
    }

}
