package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpendAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ReAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExpendAccreditationPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return ExpendAccreditationAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EXPEND_ACCREDITATION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/accreditation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        ExpendAccreditationAppealDto dto = (ExpendAccreditationAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("phoneNumber", dto.getPhoneNumber());
        parameters.put("legalAddress", profile.getLegalAddress());
        parameters.put("fullName", profile.getFullName());
        parameters.put("responsiblePersonName", dto.getResponsiblePersonName());
        parameters.put("certificateDate", dto.getCertificateDate().toString());
        parameters.put("certificateNumber", dto.getCertificateNumber());
        parameters.put("certificateValidityDate", dto.getCertificateValidityDate().toString());
        String spheres = dto.getAccreditationSpheres()
                .stream()
                .map(sphere -> sphere.label)
                .collect(Collectors.joining("\n"));
        parameters.put("accreditationSpheres", spheres);

        return parameters;
    }
}