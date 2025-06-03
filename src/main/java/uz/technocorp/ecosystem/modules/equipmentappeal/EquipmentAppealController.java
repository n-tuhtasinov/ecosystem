package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/equipment")
@RequiredArgsConstructor
public class EquipmentAppealController {

    private final AppealService appealService;

    @PostMapping("/crane")
    public ResponseEntity<?> createCrane(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CraneDto> craneDto, HttpServletRequest request) {
        appealService.saveAndSign(user, craneDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/container")
    public ResponseEntity<?> createContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ContainerDto> containerDto, HttpServletRequest request) {
        appealService.saveAndSign(user, containerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/boiler")
    public ResponseEntity<?> createBoiler(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<BoilerDto> boilerDto, HttpServletRequest request) {
        appealService.saveAndSign(user, boilerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/elevator")
    public ResponseEntity<?> createElevator(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ElevatorDto> elevatorDto, HttpServletRequest request) {
        appealService.saveAndSign(user, elevatorDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/escalator")
    public ResponseEntity<?> createEscalator(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<EscalatorDto> escalator, HttpServletRequest request) {
        appealService.saveAndSign(user, escalator, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/cableway")
    public ResponseEntity<?> createCableway(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CablewayDto> cablewayDto, HttpServletRequest request) {
        appealService.saveAndSign(user, cablewayDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/hoist")
    public ResponseEntity<?> createHoist(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<HoistDto> hoistDto, HttpServletRequest request) {
        appealService.saveAndSign(user, hoistDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/pipeline")
    public ResponseEntity<?> createPipeline(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<PipelineDto> pipelineDto, HttpServletRequest request) {
        appealService.saveAndSign(user, pipelineDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/chemical-container")
    public ResponseEntity<?> createChemicalContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ChemicalContainerDto> chemicalContainerDto, HttpServletRequest request) {
        appealService.saveAndSign(user, chemicalContainerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/heat-pipeline")
    public ResponseEntity<?> createHeatPipeline(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<HeatPipelineDto> heatPipelineDto, HttpServletRequest request) {
        appealService.saveAndSign(user, heatPipelineDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/boiler-utilizer")
    public ResponseEntity<?> createBoilerUtilizer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<BoilerUtilizerDto> boilerUtilizerDto, HttpServletRequest request) {
        appealService.saveAndSign(user, boilerUtilizerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/lpg-container")
    public ResponseEntity<?> createLpgContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<LpgContainerDto> lpgContainerDto, HttpServletRequest request) {
        appealService.saveAndSign(user, lpgContainerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/lpg-powered")
    public ResponseEntity<?> createLpgPowered(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<LpgPoweredDto> lpgPoweredDto, HttpServletRequest request) {
        appealService.saveAndSign(user, lpgPoweredDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/attraction-passport")
    public ResponseEntity<?> createAttractionPassport(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<LpgPoweredDto> lpgPoweredDto, HttpServletRequest request) {
        appealService.saveAndSign(user, lpgPoweredDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

}
