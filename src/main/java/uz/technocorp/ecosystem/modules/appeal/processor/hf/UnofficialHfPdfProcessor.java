package uz.technocorp.ecosystem.modules.appeal.processor.hf;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.BaseAppealPdfProcessor;
import uz.technocorp.ecosystem.modules.hfappeal.unofficialregister.dto.UnofficialHfAppealDto;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;

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
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile inspectorProfile) {
        UnofficialHfAppealDto dto = (UnofficialHfAppealDto) appealDto;

        User user = getOrCreateByIdentityAndDate(dto.getLegalTin(), null);
        Profile legal = getProfile(user.getProfileId());

        OfficeViewById office = officeService.getById(inspectorProfile.getOfficeId());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("officeName", office.getName());
        parameters.put("legalName", legal.getName());
        parameters.put("tin", legal.getIdentity().toString());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("address", dto.getAddress());
        parameters.put("hfName", dto.getName());
        parameters.put("inspectorName", inspectorProfile.getName());

        return parameters;
    }
}