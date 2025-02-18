package uz.technocorp.ecosystem.modules.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;

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
    public UUID create(UserDto dto) {
        Profile profile = new Profile(dto.getTin(),
                dto.getLegalName(),
                dto.getLegalAddress(),
                dto.getFullName(),
                dto.getPin(),
                dto.getDepartmentId(),
                dto.getOfficeId(),
                dto.getRegionId(),
                dto.getDistrictId(),
                dto.getPosition(),
                dto.getPhoneNumber());
        Profile saved = profileRepository.save(profile);
        return saved.getId();
    }

    @Override
    public void update(UUID profileId, UserDto dto) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", profileId));
        profile.setLegalName(dto.getLegalName());
        profile.setLegalAddress(dto.getLegalAddress());
        profile.setFullName(dto.getFullName());
        profile.setPin(dto.getPin());
        profile.setDepartmentId(dto.getDepartmentId());
        profile.setOfficeId(dto.getOfficeId());
        profile.setRegionId(dto.getRegionId());
        profile.setDistrictId(dto.getDistrictId());
        profile.setPosition(dto.getPosition());
        profileRepository.save(profile);
    }
}
