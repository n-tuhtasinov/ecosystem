package uz.technocorp.ecosystem.modules.user.view;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.05.2025
 * @since v1.0
 */
public interface UserViewByProfile {

    UUID getId();
    Long getIdentity();
    String getName();
    String getLegalForm();
    String getLegalOwnershipType();
    String getDirectorName();
    String getAddress();
    String getPhoneNumber();
    Boolean getIsActive();
}
