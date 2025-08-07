package uz.technocorp.ecosystem.modules.equipmentappeal.deregister;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto.DeregisterEquipmentDto;
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
public class EquipmentDeregisterController {

    private final EquipmentDeregisterService service;

    @PostMapping("/deregister-pdf")
    public ResponseEntity<ApiResponse> deregisterPdf(@CurrentUser User user, @Valid @RequestBody DeregisterEquipmentDto dto) {
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", service.deregisterPdf(user, dto)));
    }

    @PostMapping("/deregister")
    public ResponseEntity<ApiResponse> deregister(@CurrentUser User user, @Valid @RequestBody SignedAppealDto<DeregisterEquipmentDto> signDto, HttpServletRequest request) {
        service.deregister(user, signDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }
}
