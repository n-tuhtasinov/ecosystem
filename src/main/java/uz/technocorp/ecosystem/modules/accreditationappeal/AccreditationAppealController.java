package uz.technocorp.ecosystem.modules.accreditationappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.accreditation.AccreditationService;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.AccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpConclusionAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpendAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ReAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/accreditation")
@RequiredArgsConstructor
public class AccreditationAppealController {

    private final AppealPdfService appealPdfService;
    private final AppealService appealService;
    private final AccreditationService accreditationService;

    @PostMapping
    public ResponseEntity<?> createAccreditationAppeal(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<AccreditationAppealDto> accreditationDto, HttpServletRequest request) {
        AccreditationAppealDto accreditationAppealDto = accreditationService.setProfileInfos(user.getProfileId(), accreditationDto.getDto());
        accreditationDto.setDto(accreditationAppealDto);
        appealService.saveAndSign(user, accreditationDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/redo")
    public ResponseEntity<?> createReAccreditationAppeal(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ReAccreditationAppealDto> accreditationDto, HttpServletRequest request) {
        ReAccreditationAppealDto reAccreditationAppealDto = accreditationService.setProfileInfos(user.getProfileId(), accreditationDto.getDto());
        accreditationDto.setDto(reAccreditationAppealDto);
        appealService.saveAndSign(user, accreditationDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/expend")
    public ResponseEntity<?> createExpendAccreditationAppeal(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ExpendAccreditationAppealDto> accreditationDto, HttpServletRequest request) {
        ExpendAccreditationAppealDto expendAccreditationAppealDto = accreditationService.setProfileInfos(user.getProfileId(), accreditationDto.getDto());
        accreditationDto.setDto(expendAccreditationAppealDto);
        appealService.saveAndSign(user, accreditationDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromAccreditation(@CurrentUser User user, @Valid @RequestBody AccreditationAppealDto dto) {
        String path = appealPdfService.preparePdfWithParam(dto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/redo/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromReAccreditation(@CurrentUser User user, @Valid @RequestBody AccreditationAppealDto dto) {
        String path = appealPdfService.preparePdfWithParam(dto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/expend/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromExpendAccreditation(@CurrentUser User user, @Valid @RequestBody AccreditationAppealDto dto) {
        String path = appealPdfService.preparePdfWithParam(dto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/conclusion")
    public ResponseEntity<?> createExpertiseConclusionAppeal(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ExpConclusionAppealDto> accreditationDto, HttpServletRequest request) {
        ExpConclusionAppealDto expConclusionAppealDto = accreditationService.setProfileInfos(user.getProfileId(), accreditationDto.getDto());
        accreditationDto.setDto(expConclusionAppealDto);
        appealService.saveAndSign(user, accreditationDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/conclusion/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromExpertiseConclusion(@CurrentUser User user, @Valid @RequestBody AccreditationAppealDto dto) {
        String path = appealPdfService.preparePdfWithParam(dto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }
}
