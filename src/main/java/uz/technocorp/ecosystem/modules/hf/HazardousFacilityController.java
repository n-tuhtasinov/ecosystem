package uz.technocorp.ecosystem.modules.hf;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;
import uz.technocorp.ecosystem.modules.hf.view.HfViewById;
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
 * @created 07.03.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/hf")
@RequiredArgsConstructor
public class HazardousFacilityController {

    private final HazardousFacilityService service;

    @PutMapping("/{hfId}")
    public ResponseEntity<?> update(@PathVariable UUID hfId, @Valid @RequestBody HfDto dto) {
        service.update(hfId, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PutMapping("/periodic-update/{hfId}")
    public ResponseEntity<?> periodicUpdate(@PathVariable UUID hfId, @Valid @RequestBody HfPeriodicUpdateDto hfPeriodicUpdateDto) {
        try {
            service.periodicUpdate(hfId, hfPeriodicUpdateDto);
            return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findAllByUser(@CurrentUser User user, @RequestParam(required = false, defaultValue = "") String registryNumber) {
        List<HfSelectView> allByProfile = service.findAllByUser(user, registryNumber);
        return ResponseEntity.ok(new ApiResponse(allByProfile));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@CurrentUser User user,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "regionId", required = false) Integer regionId,
                                    @RequestParam(value = "districtId", required = false) Integer districtId,
                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                    @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                    @RequestParam(value = "mode", required = false) String mode
    ) {
        Page<HfCustom> all = service.getAll(user, new HfParams(page, size, search, regionId, districtId, startDate, endDate, mode));
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/{hfId}")
    public ResponseEntity<?> getById(@PathVariable UUID hfId) {
        HfViewById byId = service.getById(hfId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }


    @GetMapping("/risk-assessment")
    public ResponseEntity<?> getAllForRiskAssessment(@CurrentUser User user,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                     @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                     @RequestParam(value = "intervalId") Integer intervalId,
                                                     @RequestParam(value = "isAssigned", required = false) Boolean isAssigned
    ) {
        Page<HfPageView> all = service.getAllForRiskAssessment(user, page, size, legalTin, registryNumber, isAssigned, intervalId);
        return ResponseEntity.ok(new ApiResponse(all));
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount(@CurrentUser User user) {
        Long count = service.getCount(user);
        return ResponseEntity.ok(new ApiResponse(count));
    }

    @GetMapping("/export/excel")
    public void export(@CurrentUser User user, HfParams params, HttpServletResponse response) {
        service.exportExcel(user, params, response);
    }
}
