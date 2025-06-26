package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 26.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/equipment/generate-pdf")
@RequiredArgsConstructor
public class EquipmentGeneratePdfController {

    private final EquipmentAppealService equipmentAppealService;
    private final AppealPdfService appealPdfService;

    @PostMapping("/boiler")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @RequestBody BoilerDto boilerDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(boilerDto, user)));
    }

    @PostMapping("/boiler-utilizer")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody BoilerUtilizerDto boilerUtilizerDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(boilerUtilizerDto, user)));
    }

    @PostMapping("/cableway")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody CablewayDto cablewayDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(cablewayDto, user)));
    }

    @PostMapping("/chemical-container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody ChemicalContainerDto chemicalContainerDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(chemicalContainerDto, user)));
    }

    @PostMapping("/container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody ContainerDto containerDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(containerDto, user)));
    }

    @PostMapping("/crane")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody CraneDto craneDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(craneDto, user)));
    }

    @PostMapping("/elevator")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody ElevatorDto elevatorDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(elevatorDto, user)));
    }

    @PostMapping("/escalator")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody EscalatorDto escalatorDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(escalatorDto, user)));
    }

    @PostMapping("/heat-pipeline")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody HeatPipelineDto heatPipelineDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(heatPipelineDto, user)));
    }

    @PostMapping("/hoist")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody HoistDto hoistDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(hoistDto, user)));
    }

    @PostMapping("/lpg-container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody LpgContainerDto lpgContainerDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(lpgContainerDto, user)));
    }

    @PostMapping("/lpg-powered")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody LpgPoweredDto lpgPoweredDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(lpgPoweredDto, user)));
    }

    @PostMapping("/pipeline")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody PipelineDto pipelineDto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(pipelineDto, user)));
    }

    @PostMapping("/attraction-passport")
    public ResponseEntity<?> createAttractionPassport(@CurrentUser User user, @Valid @RequestBody AttractionPassportDto attractionPassportDto) {
        equipmentAppealService.setChildEquipmentNameAndChildEquipmentSortName(attractionPassportDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(attractionPassportDto, user)));
    }

    @PostMapping("/attraction")
    public ResponseEntity<?> createAttraction(@CurrentUser User user, @Valid @RequestBody AttractionDto attractionDto) {
        equipmentAppealService.setRequiredFields(attractionDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(attractionDto, user)));
    }

    private String preparePdfWithParam(AppealDto dto, User user) {
        return appealPdfService.preparePdfWithParam(dto, user);
    }
}
