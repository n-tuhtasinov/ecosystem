package uz.technocorp.ecosystem.modules.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;
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
@Slf4j
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
    public void create(UserDto dto) {

        //update profile
        UUID profileId = profileService.create(dto);

        //check list of string by Direction
        dto.getDirections().forEach(Direction::valueOf);

        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(UUID.randomUUID().toString().substring(24)),
                Role.valueOf(dto.getRole()),
                dto.getName(),
                dto.getDirections(),
                true,
                profileId);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(UUID userId, UserDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        //check list of string by Direction
        dto.getDirections().forEach(Direction::valueOf);

        //update profile
        profileService.update(user.getProfileId(), dto);

        user.setUsername(dto.getUsername());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setName(dto.getName());
        user.setDirections(dto.getDirections());
        userRepository.save(user);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void changeUserEnabled(UUID userId, Boolean enabled) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setEnabled(enabled);
        userRepository.save(user);
    }


}
