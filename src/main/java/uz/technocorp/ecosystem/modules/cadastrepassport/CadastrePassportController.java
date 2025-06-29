package uz.technocorp.ecosystem.modules.cadastrepassport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.cadastrepassport.dto.CadastrePassportParams;
import uz.technocorp.ecosystem.modules.cadastrepassport.view.CadastrePassportView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/cadastre-passports")
@RequiredArgsConstructor
public class CadastrePassportController {

    private final CadastrePassportService service;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll (@CurrentUser User user, @Valid CadastrePassportParams params) {
        Page<CadastrePassportView> page = service.getAll(user, params);
        return ResponseEntity.ok(new ApiResponse(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById (@PathVariable UUID id) {
        CadastrePassport passport = service.getById(id);
        return ResponseEntity.ok(new ApiResponse(passport));
    }
}
