package uz.technocorp.ecosystem.modules.user.view;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.05.2025
 * @since v1.0
 */
public interface UserViewByLegal {

    UUID getId();
    Long getTin();
    String getLegalName();
    String getLegalForm();
    String getLegalOwnershipType();
    String getFullName();
    String getLegalAddress();
    String getPhoneNumber();
    Boolean getIsActive();
}
