package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.ContainerDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.HeatPipelineDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class HeatPipelinePdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return HeatPipelineDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/heat-pipeline";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        HeatPipelineDto dto = (HeatPipelineDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("tin", profile.getTin().toString());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());

        return parameters;
    }
}