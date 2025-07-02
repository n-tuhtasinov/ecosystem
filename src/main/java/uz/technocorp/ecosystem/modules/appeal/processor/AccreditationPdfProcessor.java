package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.AccreditationAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.attestationappeal.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccreditationPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return AccreditationAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.ACCREDITATION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/accreditation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        AccreditationAppealDto dto = (AccreditationAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("phoneNumber", dto.getPhoneNumber());
        parameters.put("legalAddress", profile.getLegalAddress());
        parameters.put("fullName", profile.getFullName());
        parameters.put("responsiblePersonName", dto.getResponsiblePersonName());

        return parameters;
    }
}