package uz.technocorp.ecosystem.modules.equipment;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
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
@RequestMapping("/api/v1/event/equipment")
@RequiredArgsConstructor
public class EquipmentEventController {

    private final EquipmentServiceImpl service;
    private final AppealRepository appealRepository;

    @Operation(summary = "Equipment Reestr PDF shakllanishini tekshiradigan API (test uchun)")
    @GetMapping("/{appealId}")
    public String createEquipmentRegistryPdf(@PathVariable UUID appealId) {
        Optional<Appeal> appealOpl = appealRepository.findById(appealId);
        if (appealOpl.isPresent()) {
            Appeal appeal = appealOpl.get();

            EquipmentDto dto = JsonParser.parseJsonData(appeal.getData(), EquipmentDto.class);
            EquipmentInfoDto info = service.getEquipmentInfoByAppealType(appeal.getAppealType(), RegistrationMode.OFFICIAL);

            EquipmentRegistryDto registryDto = new EquipmentRegistryDto();
            registryDto.setType(info.equipmentType());
            registryDto.setRegistryNumber(info.registryNumber());
            registryDto.setRegistrationDate(LocalDate.now());
            registryDto.setManufacturedAt(dto.manufacturedAt());
            registryDto.setFactory(dto.factory());
            registryDto.setFactoryNumber(dto.factoryNumber());
            registryDto.setModel(dto.model());
            registryDto.setParameters(dto.parameters());
            registryDto.setAttractionName(dto.attractionName());
            registryDto.setRiskLevel(dto.riskLevel());

            return service.createEquipmentRegistryPdf(appeal, registryDto);
        }
        return "Bunday ID li ariza topilmadi";
    }
}
