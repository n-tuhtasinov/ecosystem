package uz.technocorp.ecosystem.modules.appeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealSearchCriteria;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals")
@RequiredArgsConstructor
public class AppealController {

    private final AppealService service;
    private final AppealCustomRepositoryImpl customRepository;

    @PatchMapping("/set-inspector")
    public ResponseEntity<?> setInspector(@Valid @RequestBody SetInspectorDto dto) {
        service.setInspector(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> changeAppealStatus(@Valid @RequestBody AppealStatusDto dto) {
        service.changeAppealStatus(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAppeals(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                           @RequestParam(value = "date", defaultValue = "") String date,
                                           @RequestParam(value = "status", defaultValue = "") String status,
                                           @RequestParam(value = "appealType", defaultValue = "") String appealType,
                                           @RequestParam(value = "legalTin", defaultValue = "") String legalTin,
                                           @RequestParam(value = "officeId", required = false) Integer officeId) {
        AppealSearchCriteria criteria = new AppealSearchCriteria(status, appealType, date, legalTin, officeId);
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "createdAt");
        return ResponseEntity.ok(customRepository.appealCustoms(pageable, criteria));

    }

}
