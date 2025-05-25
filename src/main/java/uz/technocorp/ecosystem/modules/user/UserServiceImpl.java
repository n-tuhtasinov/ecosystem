package uz.technocorp.ecosystem.modules.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.user.helper.UserViewByInspectorPin;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.department.Department;
import uz.technocorp.ecosystem.modules.department.DepartmentRepository;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.modules.user.enums.Direction;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.modules.user.helper.CommitteeUserHelper;
import uz.technocorp.ecosystem.modules.user.helper.OfficeUserHelper;
import uz.technocorp.ecosystem.modules.user.helper.UserHelperById;

import java.util.Map;
import java.util.Set;
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
    private final ProfileRepository profileRepository;
    private final DepartmentRepository departmentRepository;
    private final OfficeRepository officeRepository;

    @Override
    public UserMeDto getMe(User user) {
        return new UserMeDto(user.getId(), user.getName(), user.getRole().name(), user.getDirections());
    }

    @Override
    @Transactional
    public User create(UserDto dto) {

        //update profile
        UUID profileId = profileService.create(dto);

        //check list of string by Direction
        dto.getDirections().forEach(Direction::valueOf);

        return userRepository.save(User.builder()
                .username(dto.getUsername())
//                .password(passwordEncoder.encode(UUID.randomUUID().toString().substring(24)))
                .password(passwordEncoder.encode("root1234")) //TODO: vaqtinchalik password, keyinchalik udalit qilish kerak
                .role(Role.valueOf(dto.getRole()))
                .name(dto.getName())
                .directions(dto.getDirections())
                .enabled(true)
                .profileId(profileId)
                .build());
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

    @Override
    public void updateLegalUser(UUID userId, LegalUserDto dto) {
        //TODO: Legal userni ma'lumotlarini soliq bilan integratsiya orqali yangilab qo'yish
    }

    @Override
    public Page<CommitteeUserHelper> getCommitteeUsers(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "name");
        Page<User> users = userRepository.findAllByRoles(pageable, Set.of(Role.MANAGER, Role.HEAD, Role.CHAIRMAN));
        return users.map(this::convertToCommitteeView);
    }

    @Override
    public Page<OfficeUserHelper> getOfficeUsers(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "name");
        return userRepository.findAllByRoles(pageable, Set.of(Role.REGIONAL, Role.INSPECTOR)).map(this::convertToOfficeView);
    }

    @Override
    public UserHelperById getById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
        return new UserHelperById(user.getId(), profile.getFullName(), profile.getPin(), user.getRole().name(), user.getDirections(), profile.getDepartmentId(), profile.getOfficeId(), profile.getPosition(), profile.getPhoneNumber(), user.isEnabled());
    }


    private CommitteeUserHelper convertToCommitteeView(User user) {
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
        Department department = departmentRepository.findById(profile.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department", "id", profile.getDepartmentId()));
        return new CommitteeUserHelper(user.getId(), profile.getFullName(), profile.getPin(), user.getRole().name(), user.getDirections(), department.getName(), profile.getDepartmentId(), profile.getPosition(), profile.getPhoneNumber(), user.isEnabled());
    }

    private OfficeUserHelper convertToOfficeView(User user) {
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
        Office office = officeRepository.findById(profile.getOfficeId()).orElseThrow(() -> new ResourceNotFoundException("Office", "id", profile.getOfficeId()));
        return new OfficeUserHelper(user.getId(), profile.getFullName(), profile.getPin(), user.getRole().name(), user.getDirections(), office.getName(), office.getId(), profile.getPosition(), profile.getPhoneNumber(), user.isEnabled());
    }

    @Override
    public UserViewByInspectorPin getInspectorByPin(long pin) {
        return userRepository.getInspectorByPin(pin, Role.INSPECTOR).orElseThrow(() -> new ResourceNotFoundException("User (roli inspector bo'lgan)", "pin", pin));
    }


}
