package uz.technocorp.ecosystem.modules.cadastreappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.CadastrePassportDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals/cadastre")
@RequiredArgsConstructor
public class CadastreAppealController {

    private final AppealService appealService;
    private final AppealPdfService appealPdfService;

    @PostMapping("/passport/generate-pdf")
    public ResponseEntity<?> createPassport(@CurrentUser User user, @Valid @RequestBody CadastrePassportDto cadastrePassportDto) {
        String path = appealPdfService.preparePdfWithParam(cadastrePassportDto, user);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }
}
