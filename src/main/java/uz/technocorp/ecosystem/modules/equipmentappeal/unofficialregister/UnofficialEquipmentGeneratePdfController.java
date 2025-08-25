package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.EquipmentAppealService;
import uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 22.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/equipment/unofficial/generate-pdf")
@RequiredArgsConstructor
public class UnofficialEquipmentGeneratePdfController {

    private final EquipmentAppealService equipmentAppealService;
    private final AppealPdfService appealPdfService;
    private final UnofficialEquipmentAppealService service;

    @PostMapping("/crane")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialCraneDto craneDto) {
        service.setHfNameAndChildEquipmentName(craneDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(craneDto, user)));
    }

    @PostMapping("/boiler")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @RequestBody UnofficialBoilerDto boilerDto) {
        service.setHfNameAndChildEquipmentName(boilerDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(boilerDto, user)));
    }

    @PostMapping("/boiler-utilizer")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialBoilerUtilizerDto boilerUtilizerDto) {
        service.setHfNameAndChildEquipmentName(boilerUtilizerDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(boilerUtilizerDto, user)));
    }

    @PostMapping("/cableway")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialCablewayDto cablewayDto) {
        service.setHfNameAndChildEquipmentName(cablewayDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(cablewayDto, user)));
    }

    @PostMapping("/chemical-container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialChemicalContainerDto chemicalContainerDto) {
        service.setHfNameAndChildEquipmentName(chemicalContainerDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(chemicalContainerDto, user)));
    }

    @PostMapping("/container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialContainerDto containerDto) {
        service.setHfNameAndChildEquipmentName(containerDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(containerDto, user)));
    }

    @PostMapping("/elevator")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialElevatorDto elevatorDto) {
        service.setHfNameAndChildEquipmentName(elevatorDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(elevatorDto, user)));
    }

    @PostMapping("/escalator")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialEscalatorDto escalatorDto) {
        service.setHfNameAndChildEquipmentName(escalatorDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(escalatorDto, user)));
    }

    @PostMapping("/heat-pipeline")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialHeatPipelineDto heatPipelineDto) {
        service.setHfNameAndChildEquipmentName(heatPipelineDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(heatPipelineDto, user)));
    }

    @PostMapping("/hoist")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialHoistDto hoistDto) {
        service.setHfNameAndChildEquipmentName(hoistDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(hoistDto, user)));
    }

    @PostMapping("/lpg-container")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialLpgContainerDto lpgContainerDto) {
        service.setHfNameAndChildEquipmentName(lpgContainerDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(lpgContainerDto, user)));
    }

    @PostMapping("/lpg-powered")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialLpgPoweredDto lpgPoweredDto) {
        service.setHfNameAndChildEquipmentName(lpgPoweredDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(lpgPoweredDto, user)));
    }

    @PostMapping("/pipeline")
    public ResponseEntity<ApiResponse> generatePdf(@CurrentUser User user, @Valid @RequestBody UnofficialPipelineDto pipelineDto) {
        service.setHfNameAndChildEquipmentName(pipelineDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(pipelineDto, user)));
    }

    @PostMapping("/attraction")
    public ResponseEntity<?> createAttraction(@CurrentUser User user, @Valid @RequestBody UnofficialAttractionDto attractionDto) {
        service.setRequiredFields(attractionDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", preparePdfWithParam(attractionDto, user)));
    }

    private String preparePdfWithParam(AppealDto dto, User user) {
        return appealPdfService.preparePdfWithParam(dto, user);
    }
}
