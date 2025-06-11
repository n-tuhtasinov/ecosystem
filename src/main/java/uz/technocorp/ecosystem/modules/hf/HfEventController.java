package uz.technocorp.ecosystem.modules.hf;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 11.06.2025
 * @description PDF generatsiya qilishni test qilib ko'rish uchun
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/event/hf")
@RequiredArgsConstructor
public class HfEventController {

    private final AppealRepository appealRepository;
    private final HazardousFacilityServiceImpl service;

    @Operation(summary = "HF Reestr PDF shakllanishini tekshiradigan API (test uchun)")
    @GetMapping("/{appealId}")
    public String createHfRegistryPdf(@PathVariable UUID appealId) {
        Optional<Appeal> appealOpl = appealRepository.findById(appealId);
        if (appealOpl.isPresent()) {
            Appeal appeal = appealOpl.get();

            String registryNumber = "123-TEST-XICHO";
            HfAppealDto hfAppealDto = JsonParser.parseJsonData(appeal.getData(), HfAppealDto.class);

            return service.createHfRegistryPdf(appeal, registryNumber, hfAppealDto);
        }
        return "Bunday ID li ariza topilmadi";
    }
}
