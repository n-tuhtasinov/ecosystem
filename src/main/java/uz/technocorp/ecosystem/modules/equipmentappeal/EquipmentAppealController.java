package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.*;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

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
    public ResponseEntity<?> createCrane (@CurrentUser User user, @Valid @RequestBody CraneDto craneDto) {
        appealService.create(craneDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/container")
    public ResponseEntity<?> createContainer (@CurrentUser User user, @Valid @RequestBody ContainerDto containerDto) {
        appealService.create(containerDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/boiler")
    public ResponseEntity<?> createBoiler (@CurrentUser User user, @Valid @RequestBody BoilerDto boilerDto) {
        appealService.create(boilerDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/elevator")
    public ResponseEntity<?> createElevator (@CurrentUser User user, @Valid @RequestBody ElevatorDto elevatorDto) {
        appealService.create(elevatorDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/escalator")
    public ResponseEntity<?> createEscalator (@CurrentUser User user, @Valid @RequestBody ElevatorDto elevatorDto) {
        appealService.create(elevatorDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/cableway")
    public ResponseEntity<?> createCableway (@CurrentUser User user, @Valid @RequestBody CablewayDto cablewayDto) {
        appealService.create(cablewayDto,user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

}
