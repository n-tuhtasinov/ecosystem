package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class HfAppealPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return HfAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.XICHO_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/hf-appeals";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto dto, Profile profile) {
        HfAppealDto hfDto = (HfAppealDto) dto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("tin", profile.getTin().toString());
        parameters.put("regionName", getRegion(hfDto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(hfDto.getDistrictId()).getName());
        parameters.put("hfName", hfDto.getName());

        return parameters;
    }
}