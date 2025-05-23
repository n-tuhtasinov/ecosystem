package uz.technocorp.ecosystem.modules.appeal.view;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 16.05.2025
 * @since v1.0
 */
public interface AppealViewById {
    String getId();
    LocalDateTime getCreatedAt();
    String getAppealType();
    String getNumber();
    String getRegionName();
    String getDistrictName();
    String getAddress();
    String getExecutorName();
    LocalDate getDeadline();
    String getOfficeName();
    String getStatus();
    JsonNode getData();



}
