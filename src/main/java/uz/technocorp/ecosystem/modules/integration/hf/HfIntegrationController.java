package uz.technocorp.ecosystem.modules.integration.hf;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;
import uz.technocorp.ecosystem.modules.integration.hf.dto.HfInfoDto;

/**
 * @author Suxrob
 * @version 1.0
 * @created 08.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/integration/hf")
@RequiredArgsConstructor
public class HfIntegrationController {

    private final HfIntegrationService service;

    @GetMapping("/{tin}")
    public ResponseEntity<InfoDto<HfInfoDto>> getHfByTin(@PathVariable String tin) {
        return ResponseEntity.ok(service.getHfInfo(tin));
    }
}
