package uz.technocorp.ecosystem.modules.appeal.view;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 16.05.2025
 * @since v1.0
 */
public interface AppealViewById {
    String getId();
    LocalDate createdAt();
    String getAppealType();
    String getNumber();
    String getRegionName();
    String getDistrictName();
    String getAddress();
    String getExecutorName();
    LocalDate getDeadline();
    String getOfficeName();



}
