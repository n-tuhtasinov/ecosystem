package uz.technocorp.ecosystem.modules.profile.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.05.2025
 * @since v1.0
 */
public interface ProfileInfoView {

    UUID getId();

    Long getTin();

    String getLegalName();

    String getLegalAddress();

    String getFullName();

    Long getPin();

    Integer getRegionId();

    String getRegionName();

    Integer getDistrictId();

    String getDistrictName();

    String getPhoneNumber();
}
