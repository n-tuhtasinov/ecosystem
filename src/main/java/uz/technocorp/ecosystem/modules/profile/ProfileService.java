package uz.technocorp.ecosystem.modules.profile;


import uz.technocorp.ecosystem.modules.prevention.dto.PagingDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
public interface ProfileService {
    UUID create(UserDto dto);

    void update(UUID profileId, UserDto dto);

    Integer getOfficeId(UUID profileId);

    PagingDto<ProfileView> getProfilesForPrevention(Integer inspectorOfficeId, PreventionParamsDto params);
}
