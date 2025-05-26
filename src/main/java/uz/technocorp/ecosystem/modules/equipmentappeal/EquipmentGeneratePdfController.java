package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
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

    private final AppealService appealService;

    @PostMapping("/boiler")
    public ResponseEntity<ApiResponse> generateBoilerPdf(@CurrentUser User user, @Valid @RequestBody BoilerDto boilerDto) {
        String path = appealService.preparePdfWithParam(boilerDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/boiler-utilizer")
    public ResponseEntity<ApiResponse> generateBoilerUtilizerPdf(@CurrentUser User user, @Valid @RequestBody BoilerUtilizerDto boilerUtilizerDto) {
        String path = appealService.preparePdfWithParam(boilerUtilizerDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/cableway")
    public ResponseEntity<ApiResponse> generateCablewayPdf(@CurrentUser User user, @Valid @RequestBody CablewayDto cablewayDto) {
        String path = appealService.preparePdfWithParam(cablewayDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/chemical-container")
    public ResponseEntity<ApiResponse> generateChemicalContainerPdf(@CurrentUser User user, @Valid @RequestBody ChemicalContainerDto chemicalContainerDto) {
        String path = appealService.preparePdfWithParam(chemicalContainerDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/container")
    public ResponseEntity<ApiResponse> generateContainerPdf(@CurrentUser User user, @Valid @RequestBody ContainerDto containerDto) {
        String path = appealService.preparePdfWithParam(containerDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/crane")
    public ResponseEntity<ApiResponse> generateCranePdf(@CurrentUser User user, @Valid @RequestBody CraneDto craneDto) {
        String path = appealService.preparePdfWithParam(craneDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/elevator")
    public ResponseEntity<ApiResponse> generateElevatorPdf(@CurrentUser User user, @Valid @RequestBody ElevatorDto elevatorDto) {
        String path = appealService.preparePdfWithParam(elevatorDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/escalator")
    public ResponseEntity<ApiResponse> generateEscalatorPdf(@CurrentUser User user, @Valid @RequestBody EscalatorDto escalatorDto) {
        String path = appealService.preparePdfWithParam(escalatorDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/heat-pipeline")
    public ResponseEntity<ApiResponse> generateHeatPipelinePdf(@CurrentUser User user, @Valid @RequestBody HeatPipelineDto heatPipelineDto) {
        String path = appealService.preparePdfWithParam(heatPipelineDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/hoist")
    public ResponseEntity<ApiResponse> generateHoistPdf(@CurrentUser User user, @Valid @RequestBody HoistDto hoistDto) {
        String path = appealService.preparePdfWithParam(hoistDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/lpg-container")
    public ResponseEntity<ApiResponse> generateLpgContainerPdf(@CurrentUser User user, @Valid @RequestBody LpgContainerDto lpgContainerDto) {
        String path = appealService.preparePdfWithParam(lpgContainerDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/lpg-powered")
    public ResponseEntity<ApiResponse> generateLpgPoweredPdf(@CurrentUser User user, @Valid @RequestBody LpgPoweredDto lpgPoweredDto) {
        String path = appealService.preparePdfWithParam(lpgPoweredDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/pipeline")
    public ResponseEntity<ApiResponse> generatePipelinePdf(@CurrentUser User user, @Valid @RequestBody PipelineDto pipelineDto) {
        String path = appealService.preparePdfWithParam(pipelineDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }
}
