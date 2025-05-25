package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.CraneDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class CraneAppealPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return CraneDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/crane-appeals";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto dto, Profile profile) {
        CraneDto craneDto = (CraneDto) dto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("tin", profile.getTin().toString());
        parameters.put("regionName", getRegion(craneDto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(craneDto.getDistrictId()).getName());

        return parameters;
    }
}