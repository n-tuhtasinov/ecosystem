package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister;

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
import uz.technocorp.ecosystem.modules.equipmentappeal.register.EquipmentAppealService;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 22.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/equipment/unofficial")
@RequiredArgsConstructor
public class UnofficialEquipmentAppealController {

    private final AppealService appealService;
    private final EquipmentAppealService equipmentAppealService;

    @PostMapping("/crane")
    public ResponseEntity<?> createCrane(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CraneDto> craneDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(craneDto.getDto());
        appealService.saveAndSign(user, craneDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/container")
    public ResponseEntity<?> createContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ContainerDto> containerDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(containerDto.getDto());
        appealService.saveAndSign(user, containerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/boiler")
    public ResponseEntity<?> createBoiler(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<BoilerDto> boilerDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(boilerDto.getDto());
        appealService.saveAndSign(user, boilerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/elevator")
    public ResponseEntity<?> createElevator(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ElevatorDto> elevatorDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(elevatorDto.getDto());
        appealService.saveAndSign(user, elevatorDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/escalator")
    public ResponseEntity<?> createEscalator(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<EscalatorDto> escalator, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(escalator.getDto());
        appealService.saveAndSign(user, escalator, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/cableway")
    public ResponseEntity<?> createCableway(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CablewayDto> cablewayDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(cablewayDto.getDto());
        appealService.saveAndSign(user, cablewayDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/hoist")
    public ResponseEntity<?> createHoist(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<HoistDto> hoistDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(hoistDto.getDto());
        appealService.saveAndSign(user, hoistDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/pipeline")
    public ResponseEntity<?> createPipeline(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<PipelineDto> pipelineDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(pipelineDto.getDto());
        appealService.saveAndSign(user, pipelineDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/chemical-container")
    public ResponseEntity<?> createChemicalContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ChemicalContainerDto> chemicalContainerDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(chemicalContainerDto.getDto());
        appealService.saveAndSign(user, chemicalContainerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/heat-pipeline")
    public ResponseEntity<?> createHeatPipeline(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<HeatPipelineDto> heatPipelineDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(heatPipelineDto.getDto());
        appealService.saveAndSign(user, heatPipelineDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/boiler-utilizer")
    public ResponseEntity<?> createBoilerUtilizer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<BoilerUtilizerDto> boilerUtilizerDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(boilerUtilizerDto.getDto());
        appealService.saveAndSign(user, boilerUtilizerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/lpg-container")
    public ResponseEntity<?> createLpgContainer(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<LpgContainerDto> lpgContainerDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(lpgContainerDto.getDto());
        appealService.saveAndSign(user, lpgContainerDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/lpg-powered")
    public ResponseEntity<?> createLpgPowered(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<LpgPoweredDto> lpgPoweredDto, HttpServletRequest request) {
        equipmentAppealService.setHfNameAndChildEquipmentName(lpgPoweredDto.getDto());
        appealService.saveAndSign(user, lpgPoweredDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/attraction-passport")
    public ResponseEntity<?> createAttractionPassport(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<AttractionPassportDto> attractionPassportDto, HttpServletRequest request) {
        equipmentAppealService.setChildEquipmentNameAndChildEquipmentSortName(attractionPassportDto.getDto());
        appealService.saveAndSign(user, attractionPassportDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/attraction")
    public ResponseEntity<?> createAttraction(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<AttractionDto> attractionDto, HttpServletRequest request) {
        equipmentAppealService.setRequiredFields(attractionDto.getDto());
        appealService.saveAndSign(user, attractionDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

}
