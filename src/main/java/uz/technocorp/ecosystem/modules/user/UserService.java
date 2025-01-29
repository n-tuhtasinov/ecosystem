package uz.technocorp.ecosystem.modules.user;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface UserService {
    Map<String, Object> getMe(User user);
}
