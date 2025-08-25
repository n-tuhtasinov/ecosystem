package uz.technocorp.ecosystem.modules.hfappeal.unofficialregister;

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
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.hfappeal.unofficialregister.dto.UnofficialHfAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/hf/unofficial")
@RequiredArgsConstructor
public class UnofficialHfAppealController {

    private final AppealPdfService appealPdfService;
    private final AppealService appealService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createAndSign(@Valid @RequestBody SignedAppealDto<UnofficialHfAppealDto> signedDto, HttpServletRequest request) {
        // Set hfTypeName
        if (signedDto.getDto().getHfTypeId() != null)
            signedDto.getDto().setHfTypeName(appealService.setHfTypeName(signedDto.getDto().getHfTypeId()));

        User legal = userService.getOrCreateByIdentityAndDate(signedDto.getDto().getLegalTin(), null);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED, appealService.saveAndSign(legal, signedDto, request)));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody UnofficialHfAppealDto hfDto) {
        hfDto.setInspector(user);
        User legal = userService.getOrCreateByIdentityAndDate(hfDto.getLegalTin(), null);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", appealPdfService.preparePdfWithParam(hfDto, legal)));
    }
}
