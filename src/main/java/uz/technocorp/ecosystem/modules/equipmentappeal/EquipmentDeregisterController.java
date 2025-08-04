//package uz.technocorp.ecosystem.modules.equipmentappeal;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import uz.technocorp.ecosystem.modules.appeal.AppealService;
//import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
//import uz.technocorp.ecosystem.modules.equipmentappeal.dto.*;
//import uz.technocorp.ecosystem.modules.user.User;
//import uz.technocorp.ecosystem.security.CurrentUser;
//import uz.technocorp.ecosystem.shared.ApiResponse;
//import uz.technocorp.ecosystem.shared.ResponseMessage;
//
///**
// * @author Sukhrob
// * @version 1.0
// * @created 01.08.2025
// * @since v1.0
// */
//@RestController
//@RequestMapping("/api/v1/appeals/equipment")
//@RequiredArgsConstructor
//public class EquipmentDeregisterController {
//
//    private final AppealService appealService;
//    private final EquipmentAppealService equipmentAppealService;
//
//    @PostMapping("/deregister")
//    public ResponseEntity<?> deregister(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<CraneDto> craneDto, HttpServletRequest request) {
//        equipmentAppealService.setHfNameAndChildEquipmentName(craneDto.getDto());
//        appealService.saveAndSign(user, craneDto, request);
//        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
//    }
//
//
//}
