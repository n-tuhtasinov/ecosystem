package uz.technocorp.ecosystem.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.dto.DepartmentalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.modules.user.enums.Direction;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserMeDto getMe(User user) {
        return new UserMeDto(user.getId(), user.getName(), user.getRole().name(), user.getDirections());
    }

    @Override
    @Transactional
    public void saveDepartmentalUser(DepartmentalUserDto user) {
        UUID profileId = profileService.save(user);


        User user1 = User.builder()
                .username(user.pin().toString())
                .password(passwordEncoder.encode(UUID.randomUUID().toString().substring(24)))
                .role(Role.valueOf(user.role()))
                .name(user.fullName())
                .directions(user.directions())
                .profileId(profileId)
                .enabled(true)
                .build();

    }
}
