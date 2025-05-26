package uz.technocorp.ecosystem.modules.appeal.processor;

import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.user.User;

public interface AppealPdfProcessor {
    String preparePdfWithParam(AppealDto dto, User user);
    Class<? extends AppealDto> getSupportedType();
}