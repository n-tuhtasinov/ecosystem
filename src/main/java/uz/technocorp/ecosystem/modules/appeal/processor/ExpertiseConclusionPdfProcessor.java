package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.AccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpConclusionAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExpertiseConclusionPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return ExpConclusionAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EXPERTISE_CONCLUSION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/accreditation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        ExpConclusionAppealDto dto = (ExpConclusionAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("phoneNumber", dto.getPhoneNumber());
        parameters.put("legalAddress", profile.getLegalAddress());
        parameters.put("fullName", profile.getFullName());
        parameters.put("certificateDate", dto.getCertificateDate().toString());
        parameters.put("certificateNumber", dto.getCertificateNumber());
        parameters.put("certificateValidityDate", dto.getCertificateValidityDate().toString());
        parameters.put("responsiblePersonName", dto.getResponsiblePersonName());

        parameters.put("firstSymbolsGroup", dto.getFirstSymbolsGroup());
        parameters.put("secondSymbolsGroup", dto.getSecondSymbolsGroup());
        parameters.put("thirdSymbolsGroup", dto.getThirdSymbolsGroup());
        parameters.put("regionName", dto.getRegionName());
        parameters.put("districtName", dto.getDistrictName());
        parameters.put("objectAddress", dto.getObjectAddress());
        parameters.put("expertiseObjectName", dto.getExpertiseObjectName());

        return parameters;
    }
}