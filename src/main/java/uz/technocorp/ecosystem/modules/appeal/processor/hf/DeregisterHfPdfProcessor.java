package uz.technocorp.ecosystem.modules.appeal.processor.hf;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.BaseAppealPdfProcessor;
import uz.technocorp.ecosystem.modules.hfappeal.deregister.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeregisterHfPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return HfDeregisterDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.DEREGISTER_HF_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/hf-appeals/deregister";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        HfDeregisterDto dto = (HfDeregisterDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalAddress", profile.getAddress());
        parameters.put("legalName", profile.getName());
        parameters.put("legalTin", profile.getIdentity().toString());
        parameters.put("registryNumber", dto.getRegistryNumber());
        parameters.put("address", dto.getAddress());
        parameters.put("hfName", dto.getHfName());
        parameters.put("reasons", dto.getReasons());
        parameters.put("directorName", profile.getDirectorName());

        return parameters;
    }
}