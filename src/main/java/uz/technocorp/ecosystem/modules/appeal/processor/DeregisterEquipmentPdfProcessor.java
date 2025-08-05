package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto.DeregisterEquipmentDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeregisterEquipmentPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return DeregisterEquipmentDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.DEREGISTER_EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/equipment/deregister";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        DeregisterEquipmentDto dto = (DeregisterEquipmentDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getName());
        parameters.put("identity", profile.getIdentity().toString());
        parameters.put("ownerAddress", profile.getAddress());
        parameters.put("directorName", profile.getDirectorName());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("model", dto.getModel());
        parameters.put("equipmentType", dto.getType().value);
        parameters.put("oldRegistryNumber", dto.getRegistryNumber());
        parameters.put("description", dto.getDescription());

        return parameters;
    }
}