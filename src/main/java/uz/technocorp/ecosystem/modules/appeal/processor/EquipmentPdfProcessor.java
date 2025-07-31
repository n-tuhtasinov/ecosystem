package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.EquipmentAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class EquipmentPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return EquipmentAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/equipment";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        EquipmentAppealDto dto = (EquipmentAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getName());
        parameters.put("legalTin", profile.getIdentity().toString());
        parameters.put("facilityName", dto.getHazardousFacilityName() != null ? dto.getHazardousFacilityName() : "-");
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("address", dto.getAddress());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("model", dto.getModel());
        parameters.put("factory", dto.getFactory());
        parameters.put("equipmentType", dto.getType().value);
        parameters.put("fullName", profile.getDirectorName());

        return parameters;
    }
}