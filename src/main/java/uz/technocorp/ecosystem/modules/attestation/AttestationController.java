package uz.technocorp.ecosystem.modules.attestation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationConductDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationParamsDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationPendingParamsDto;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationDirection;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDto;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/attestation")
@RequiredArgsConstructor
public class AttestationController {

    private final HazardousFacilityService hfService;
    private final AppealPdfService appealPdfService;
    private final AppealService appealService;
    private final AttestationService service;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@CurrentUser User user, @Valid AttestationParamsDto dto) {
        return ResponseEntity.ok(new ApiResponse(service.getAllByParams(user, dto)));
    }

    @GetMapping("/{attestationId}")
    public ResponseEntity<ApiResponse> get(@CurrentUser User user, @PathVariable UUID attestationId) {
        return ResponseEntity.ok().body(new ApiResponse(service.getById(user, attestationId)));
    }

    @GetMapping("/by-appeal/{appealId}")
    public ResponseEntity<ApiResponse> getByAppeal(@CurrentUser User user, @PathVariable UUID appealId) {
        return ResponseEntity.ok().body(new ApiResponse(service.getByAppeal(user, appealId)));
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse> getAllPending(@CurrentUser User user, @Valid AttestationPendingParamsDto dto) {
        return ResponseEntity.ok(new ApiResponse(service.getAllPending(user, dto)));
    }

    //TODO   ROLE -> LEGAL
    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody AttestationDto attestationDto) {
        setDynamicRows(attestationDto);
        String path = appealPdfService.preparePdfWithParam(attestationDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    //TODO   ROLE -> LEGAL
    @PostMapping
    public ResponseEntity<ApiResponse> create(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<AttestationDto> signedDto, HttpServletRequest request) {
        signedDto.getDto().setFiles(Map.of("1", signedDto.getFilePath()));
        hfService.findByIdAndProfileId(signedDto.getDto().getHfId(), user.getProfileId());
        UUID appealId = appealService.saveAndSign(user, signedDto, request);
        return ResponseEntity.ok().body(new ApiResponse("Ariza yaratildi", appealId));
    }

    // TODO ROLE -> REGIONAL & COMMITTEE
    @PostMapping("/conduct")
    public ResponseEntity<ApiResponse> conduct(@CurrentUser User user, @Valid @RequestBody AttestationConductDto conductDto) {
        service.conduct(user, conductDto);
        return ResponseEntity.ok().body(new ApiResponse("Muvaffaqiyatli amalga oshirildi"));
    }

    private void setDynamicRows(AttestationDto attestationDto) {
        if (AttestationDirection.COMMITTEE.equals(attestationDto.getDirection())) {
            attestationDto.setDynamicRows(makeDynamicRows(attestationDto.getEmployeeList()));
        }
    }

    private String makeDynamicRows(List<EmployeeDto> list) {
        StringBuilder dynamicRows = new StringBuilder();
        int order = 1;
        for (EmployeeDto employee : list) {
            dynamicRows
                    .append("<div class=\"list-item\">")
                    .append("<div>")
                    .append(order)
                    .append(". ")
                    .append(employee.getFullName())
                    .append(" - ")
                    .append(employee.getProfession())
                    .append("</div>")
                    .append("</div>");
            order++;
        }
        return dynamicRows.toString();
    }
}
