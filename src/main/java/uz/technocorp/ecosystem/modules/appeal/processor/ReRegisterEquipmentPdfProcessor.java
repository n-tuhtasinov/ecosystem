package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto.ReRegisterEquipmentDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReRegisterEquipmentPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return ReRegisterEquipmentDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.REREGISTER_EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/equipment/reregister";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        ReRegisterEquipmentDto dto = (ReRegisterEquipmentDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getName());
        parameters.put("identity", profile.getIdentity().toString());
        parameters.put("address", dto.getAddress());
        parameters.put("directorName", profile.getDirectorName());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("manufacturedAt", String.valueOf(dto.getManufacturedAt().getYear()));
        parameters.put("model", dto.getModel());
        parameters.put("factory", dto.getFactory());
        parameters.put("equipmentType", dto.getType().value);
        parameters.put("oldRegistryNumber", dto.getOldRegistryNumber());

        return parameters;
    }
}