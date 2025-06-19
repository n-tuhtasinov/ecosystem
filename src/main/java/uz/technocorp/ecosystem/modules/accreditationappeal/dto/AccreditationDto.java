package uz.technocorp.ecosystem.modules.accreditationappeal.dto;

import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
public class AccreditationDto implements AppealDto {
    @Override
    public AppealType getAppealType() {
        return null;
    }

    @Override
    public Integer getRegionId() {
        return 0;
    }

    @Override
    public Integer getDistrictId() {
        return 0;
    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    public String getPhoneNumber() {
        return "";
    }

    @Override
    public Map<String, String> getFiles() {
        return Map.of();
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
