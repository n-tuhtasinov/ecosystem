package uz.technocorp.ecosystem.modules.irs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsParams;
import uz.technocorp.ecosystem.modules.irs.view.IrsView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 01.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/irs")
@RequiredArgsConstructor
public class IonizingRadiationSourceController {

    private final IonizingRadiationSourceService service;

//    @PostMapping("/without-appeal")
//    public ResponseEntity<?> create(@RequestBody IrsDto dto) {
//        service.create(dto);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody IrsDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.UPDATED));
    }

    @GetMapping("/risk-assessment")
    public ResponseEntity<?> getAllForRiskAssessment(@CurrentUser User user,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                     @RequestParam(value = "legalTin", required = false) Long legalTin,
                                                     @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                                     @RequestParam(value = "intervalId") Integer intervalId,
                                                     @RequestParam(value = "isAssigned") Boolean isAssigned
    ) {
        Page<HfPageView> all = service.getAllForRiskAssessment(user, page, size, legalTin, registryNumber, isAssigned, intervalId);
        return ResponseEntity.ok(new ApiResponse(all));
    }


    @GetMapping
    public ResponseEntity<?> getAll(@CurrentUser User user,
                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                    @RequestParam(value = "legalTin", required = false) Long legalTin,
                                    @RequestParam(value = "registryNumber", required = false) String registryNumber,
                                    @RequestParam(value = "regionId", required = false) Integer regionId,
                                    @RequestParam(value = "districtId", required = false) Integer districtId,
                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                    @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        Page<IrsView> all = service.getAll(user, new IrsParams(page, size, legalTin, registryNumber, regionId, districtId, startDate, endDate));
        return ResponseEntity.ok(new ApiResponse(all));
    }




}
