package uz.technocorp.ecosystem.modules.user.dto;

import uz.technocorp.ecosystem.modules.profile.enums.ProfileType;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
public interface UserDto {
    String getUsername();
    String getRole();
    List<String> getDirections();
    String getName();
    Long getIdentity();
    String getLegalName();
    String getLegalAddress();
    String getFullName();
    ProfileType getType();
    Integer getDepartmentId();
    Integer getOfficeId();
    Integer getRegionId();
    Integer getDistrictId();
    String getPosition();
    String getPhoneNumber();
    String getLegalOwnershipType();
    String getLegalForm();
}
