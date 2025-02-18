package uz.technocorp.ecosystem.modules.user;

import uz.technocorp.ecosystem.modules.user.dto.UserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface UserService {

    UserMeDto getMe(User user);

    User create(UserDto dto);

    void update(UUID userId, UserDto dto);

    void deleteById(UUID userId);

    void changeUserEnabled(UUID userId, Boolean enabled);
}
