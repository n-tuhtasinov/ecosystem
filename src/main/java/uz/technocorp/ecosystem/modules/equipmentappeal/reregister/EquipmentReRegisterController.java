package uz.technocorp.ecosystem.modules.equipmentappeal.reregister;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto.ReRegisterEquipmentDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 01.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/equipment")
@RequiredArgsConstructor
public class EquipmentReRegisterController {

    private final EquipmentReRegisterService service;

    @PostMapping("/reregister-pdf")
    public ResponseEntity<ApiResponse> reregisterPdf(@CurrentUser User user, @Valid @RequestBody ReRegisterEquipmentDto dto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", service.reregisterPdf(user, dto)));
    }

    @PostMapping("/reregister")
    public ResponseEntity<ApiResponse> reregister(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<ReRegisterEquipmentDto> signDto, HttpServletRequest request) {
        service.reregister(user, signDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }
}
