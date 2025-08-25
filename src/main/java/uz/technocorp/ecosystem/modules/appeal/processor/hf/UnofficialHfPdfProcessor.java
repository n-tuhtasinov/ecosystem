package uz.technocorp.ecosystem.modules.appeal.processor.hf;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.BaseAppealPdfProcessor;
import uz.technocorp.ecosystem.modules.hfappeal.unofficialregister.dto.UnofficialHfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class UnofficialHfPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return UnofficialHfAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.UNOFFICIAL_HF_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/hf-appeals/unofficial";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        UnofficialHfAppealDto dto = (UnofficialHfAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getDirectorName());
        parameters.put("legalName", profile.getName());
        parameters.put("tin", profile.getIdentity().toString());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("hfName", dto.getName());
        parameters.put("inspectorName", dto.getInspector().getName());

        return parameters;
    }
}