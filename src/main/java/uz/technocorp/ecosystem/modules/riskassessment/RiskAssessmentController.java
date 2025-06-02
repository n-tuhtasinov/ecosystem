package uz.technocorp.ecosystem.modules.riskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 27.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/risk-assessments")
@RequiredArgsConstructor
public class RiskAssessmentController {

    private final RiskAssessmentService service;

    @GetMapping("/hf")
    public ResponseEntity<?> getAllHfRiskAssessments(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "tin") Long tin,
                                                     @RequestParam(value = "regionId") Integer regionId,
                                                     @RequestParam(value = "intervalId") Integer intervalId) {
        Page<RiskAssessmentView> allHf = service.getAllHf(tin, regionId, intervalId, page, size);
        return ResponseEntity.ok(new ApiResponse(allHf));
    }
    @GetMapping("/equipments")
    public ResponseEntity<?> getAllEquipmentsRiskAssessments(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "tin") Long tin,
                                                     @RequestParam(value = "regionId") Integer regionId,
                                                     @RequestParam(value = "intervalId") Integer intervalId) {
        Page<RiskAssessmentView> allEquipments = service.getAllEquipments(tin, regionId, intervalId, page, size);
        return ResponseEntity.ok(new ApiResponse(allEquipments));
    }

    @GetMapping("/irs")
    public ResponseEntity<?> getAllIrsRiskAssessments(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "tin") Long tin,
                                                     @RequestParam(value = "regionId") Integer regionId,
                                                     @RequestParam(value = "intervalId") Integer intervalId) {
        Page<RiskAssessmentView> allIrs = service.getAllIrs(tin, regionId, intervalId, page, size);
        return ResponseEntity.ok(new ApiResponse(allIrs));
    }
}
