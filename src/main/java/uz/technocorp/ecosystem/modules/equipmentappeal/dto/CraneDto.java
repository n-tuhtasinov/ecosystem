package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
public class CraneDto implements AppealDto {


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
    public LocalDate getDeadline() {
        return null;
    }
}
