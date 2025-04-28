package uz.technocorp.ecosystem.modules.riskanalysisinterval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/risk-analysis-intervals")
@RequiredArgsConstructor
public class RiskAnalysisIntervalController {

    private final RiskAnalysisIntervalService service;

    @GetMapping("/select")
    public ResponseEntity<?> findAll(@RequestParam Integer year) {
        List<RiskAnalysisInterval> all = service.findAll(year);
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping
    public ResponseEntity<?> findByStatus() {
        RiskAnalysisInterval byStatus = service.findByStatus();
        return ResponseEntity.ok(new ApiResponse(byStatus));
    }
}
