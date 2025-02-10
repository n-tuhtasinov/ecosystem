package uz.technocorp.ecosystem.modules.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.user.dto.DepartmentalUserDto;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public UUID save(DepartmentalUserDto user) {
        Profile profile = Profile.builder()
                .fullName(user.fullName())
                .pin(user.pin())
                .departmentId(user.departmentId())
                .build();
        Profile saved = profileRepository.save(profile);
        return saved.getId();
    }
}
