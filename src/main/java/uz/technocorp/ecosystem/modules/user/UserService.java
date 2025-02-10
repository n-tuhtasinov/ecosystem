package uz.technocorp.ecosystem.modules.user;

import jakarta.validation.Valid;
import uz.technocorp.ecosystem.modules.user.dto.DepartmentalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface UserService {
    UserMeDto getMe(User user);

    void saveDepartmentalUser(@Valid DepartmentalUserDto user);
}
