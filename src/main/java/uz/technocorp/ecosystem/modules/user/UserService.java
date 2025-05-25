package uz.technocorp.ecosystem.modules.user;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.modules.user.helper.CommitteeUserHelper;
import uz.technocorp.ecosystem.modules.user.helper.OfficeUserHelper;
import uz.technocorp.ecosystem.modules.user.helper.UserHelperById;
import uz.technocorp.ecosystem.modules.user.helper.UserViewByInspectorPin;

import java.util.Map;
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

    void updateLegalUser(UUID userId, LegalUserDto dto);

    Page<CommitteeUserHelper> getCommitteeUsers(Map<String, String> params);

    Page<OfficeUserHelper> getOfficeUsers(Map<String, String> params);

    UserHelperById getById(UUID userId);

    UserViewByInspectorPin getInspectorByPin(long pin);
}
