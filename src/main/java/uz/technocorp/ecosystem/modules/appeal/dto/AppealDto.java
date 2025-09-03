package uz.technocorp.ecosystem.modules.appeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
public interface AppealDto {

    @Schema(hidden = true)
    AppealType getAppealType();

    Integer getRegionId();

    Integer getDistrictId();

    String getAddress();

    String getPhoneNumber();

    Map<String, FileDto> getFiles();

    @Schema(hidden = true)
    RegistrationMode getMode();

    @Schema(hidden = true)
    LocalDate getDeadline();


}
