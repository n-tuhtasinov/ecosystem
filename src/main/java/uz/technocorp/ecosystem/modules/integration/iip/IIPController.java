package uz.technocorp.ecosystem.modules.integration.iip;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.integration.iip.dto.IndividualDto;
import uz.technocorp.ecosystem.modules.integration.iip.dto.LegalDto;
import uz.technocorp.ecosystem.modules.user.dto.IndividualUserDto;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/integration/iip")
@RequiredArgsConstructor
public class IIPController {

    private final IIPService iipService;

    @PostMapping("/legal")
    public ResponseEntity<?> getLegalInfo(@Valid @RequestBody LegalDto legalDto) {
        LegalUserDto info = iipService.getGnkInfo(legalDto.tin());
        return ResponseEntity.ok(new ApiResponse(info));
    }

    @PostMapping("/individual")
    public ResponseEntity<?> getIndividualInfo(@Valid @RequestBody IndividualDto individualDto) {
        IndividualUserDto info = iipService.getPinInfo(individualDto.pin(), individualDto.birthDate());
        return ResponseEntity.ok(new ApiResponse(info));
    }

}
