package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.CadastrePassportDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.DeclarationDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeclarationAppealPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return DeclarationDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.DECLARATION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/declaration-appeals";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        DeclarationDto dto = (DeclarationDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("directorName", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("legalTin", profile.getTin().toString());
        parameters.put("hfName", dto.getHfName());

        return parameters;
    }
}