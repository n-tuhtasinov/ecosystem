package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.BoilerDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class BoilerPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return BoilerDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/boiler";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        BoilerDto dto = (BoilerDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("tin", profile.getTin().toString());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());

        return parameters;
    }
}