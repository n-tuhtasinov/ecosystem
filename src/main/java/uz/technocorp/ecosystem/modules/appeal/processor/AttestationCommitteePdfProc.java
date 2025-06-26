package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class AttestationCommitteePdfProc extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return AttestationDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.ATTESTATION_LEADER_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/attestation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        AttestationDto dto = (AttestationDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("employees", dto.getDynamicRows());
        parameters.put("directorName", profile.getFullName());

        return parameters;
    }
}