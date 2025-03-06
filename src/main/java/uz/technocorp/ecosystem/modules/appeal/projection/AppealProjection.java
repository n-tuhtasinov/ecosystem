package uz.technocorp.ecosystem.modules.appeal.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface AppealProjection {

    UUID getId();
    String getDate();
    String getStatus();
    String getLegalTin();
    String getLegalName();
    String getRegionName();
    String getDistrictName();
    UUID getMainId();
    String getAddress();
    String getEmail();
    String getPhoneNumber();
    String getAppealType();
    String getInspectorName();
    String getDeadline();
}
