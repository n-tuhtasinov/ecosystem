package uz.technocorp.ecosystem.modules.equipment;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRiskParamsDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.AttractionPassportView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentRiskView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentViewById;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;

    @GetMapping
    public ResponseEntity<?> getAll(@CurrentUser User user,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "type", required = false) EquipmentType type,
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "regionId", required = false) Integer regionId,
                                    @RequestParam(value = "districtId", required = false) Integer districtId,
                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                    @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                    @RequestParam(value = "isActive", required = false) Boolean isActive,
                                    @RequestParam(value = "mode", required = false) String mode
    ) {
        Page<EquipmentView> all = service.getAll(user, new EquipmentParams(type, page, size, search, regionId, districtId, startDate, endDate, isActive, mode));
        return ResponseEntity.ok(new ApiResponse(all));
    }


    @GetMapping("/{equipmentId}")
    public ResponseEntity<?> getById(@PathVariable UUID equipmentId) {
        EquipmentViewById byId = service.getById(equipmentId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }

    @GetMapping("/registry-number/{oldRegistryNumber}")
    public ResponseEntity<?> getByRegistryNumber(@PathVariable String oldRegistryNumber) {
        EquipmentViewById byId = service.findByRegistryNumber(oldRegistryNumber);
        return ResponseEntity.ok(new ApiResponse(byId));
    }


    @GetMapping("/attractions/risk-assessment")
    public ResponseEntity<?> getAllAttractionsForRiskAssessment(@CurrentUser User user,
                                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                                @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                                @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                                @RequestParam(value = "intervalId") Integer intervalId,
                                                                @RequestParam(value = "isAssigned", required = false) Boolean isAssigned
    ) {
        Page<EquipmentRiskView> all = service.getAllEquipmentRiskAssessment(
                new EquipmentRiskParamsDto(EquipmentType.ATTRACTION, user, page, size, legalTin, registryNumber, isAssigned, intervalId));
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/elevators/risk-assessment")
    public ResponseEntity<?> getAllElevatorsForRiskAssessment(@CurrentUser User user,
                                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                              @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                              @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                              @RequestParam(value = "intervalId") Integer intervalId,
                                                              @RequestParam(value = "isAssigned", required = false) Boolean isAssigned
    ) {
        Page<EquipmentRiskView> all = service.getAllEquipmentRiskAssessment(
                new EquipmentRiskParamsDto(EquipmentType.ELEVATOR, user, page, size, legalTin, registryNumber, isAssigned, intervalId));
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/attraction-passport")
    public ResponseEntity<?> getAttractionPassportByRegistryNumber(@RequestParam String registryNumber) {
        AttractionPassportView view = service.getAttractionPassportByRegistryNumber(registryNumber);
        return ResponseEntity.ok(new ApiResponse(view));
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount(@CurrentUser User user) {
        Long count = service.getCount(user);
        return ResponseEntity.ok(new ApiResponse(count));
    }

    @GetMapping("/export/excel")
    public void export(@CurrentUser User user, EquipmentParams params, HttpServletResponse response) {
        service.exportExcel(user, params, response);
    }
}
