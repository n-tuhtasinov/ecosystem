package uz.technocorp.ecosystem.modules.profile;


import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
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

    Long getProfileTin(UUID profileId);

    Profile findByTin(Long tin);

    Profile getProfile(UUID profileId);

    Page<ProfileView> getProfilesForPrevention(PreventionParamsDto params);

    void addPhoneNumber(UUID profileId, String phoneNumber);

    ProfileInfoView getProfileInfo(Long tin);

    boolean existsProfileByTin (Long tin);
}
