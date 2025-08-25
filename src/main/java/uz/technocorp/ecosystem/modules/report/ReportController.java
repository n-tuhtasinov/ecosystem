package uz.technocorp.ecosystem.modules.report;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.report.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.report.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.report.dto.response.ReportByRegistryDto;
import uz.technocorp.ecosystem.modules.report.view.ReportByAppealStatusView;
import uz.technocorp.ecosystem.modules.report.view.ReportByAppealTypeView;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {


    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/appeal-status")
    public ResponseEntity<?> getAppealStatus(@Valid AppealStatusFilterDto filterDto) {
        List<ReportByAppealStatusView> count = reportService.getAppealStatus(filterDto);
        return ResponseEntity.ok(new ApiResponse(count));
    }

    @GetMapping("/appeal-type")
    public ResponseEntity<?> getAppealType(@Valid AppealTypeFilterDto filterDto) {
        List<ReportByAppealTypeView> list =  reportService.getAppealType(filterDto);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @GetMapping("/registry")
    public ResponseEntity<?> getRegistry(@RequestParam(required = false) LocalDate date) {
        List<ReportByRegistryDto> list = reportService.getRegistry(date);
        return ResponseEntity.ok(new ApiResponse(list));
    }
}
