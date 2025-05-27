package uz.technocorp.ecosystem.modules.user.view;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
public interface UserViewByInspectorPin {
    UUID getId();
    String getFullName();
    Long getPin();
    UUID getProfileId();
}
