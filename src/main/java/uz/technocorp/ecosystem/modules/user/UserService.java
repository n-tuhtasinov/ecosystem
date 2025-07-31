package uz.technocorp.ecosystem.modules.user;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.modules.user.view.*;

import java.util.List;
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

    void updateLegalUser(Long tin);

    Page<CommitteeUserView> getCommitteeUsers(Map<String, String> params);

    Page<OfficeUserView> getOfficeUsers(Map<String, String> params);

    List<InspectorDto> getInspectors(User user, Map<String, String> params);

    UserViewById getById(UUID userId);

    UserViewByInspectorPin getInspectorByPin(long pin);

    UserViewByProfile getLegalOrIndividualUserByIdentity(Long identity);

    User findById(UUID id);
}
