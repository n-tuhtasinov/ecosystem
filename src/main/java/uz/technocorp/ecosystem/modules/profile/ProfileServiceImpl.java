package uz.technocorp.ecosystem.modules.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
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
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Override
    public UUID create(UserDto dto) {
        Region region = regionRepository
                .findById(dto.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", dto.getRegionId()));
        District district = districtRepository
                .findById(dto.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.getDistrictId()));

        Profile profile = new Profile(
                dto.getTin(),
                dto.getLegalName(),
                dto.getLegalAddress(),
                dto.getFullName(),
                dto.getPin(),
                dto.getDepartmentId(),
                dto.getOfficeId(),
                dto.getRegionId(),
                region.getName(),
                dto.getDistrictId(),
                district.getName(),
                dto.getPosition(),
                dto.getPhoneNumber());
        Profile saved = profileRepository.save(profile);
        return saved.getId();
    }

    @Override
    public void update(UUID profileId, UserDto dto) {
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", profileId));

        profile.setLegalName(dto.getLegalName());
        profile.setLegalAddress(dto.getLegalAddress());
        profile.setFullName(dto.getFullName());
        profile.setPin(dto.getPin());
        profile.setDepartmentId(dto.getDepartmentId());
        profile.setOfficeId(dto.getOfficeId());
        if (!dto.getRegionId().equals(profile.getRegionId())) {
            Region region = regionRepository
                    .findById(dto.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", dto.getRegionId()));
            profile.setRegionId(dto.getRegionId());
            profile.setRegionName(region.getName());
            District district = districtRepository
                    .findById(dto.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.getDistrictId()));
            profile.setDistrictId(dto.getDistrictId());
            profile.setDistrictName(district.getName());
        }
        if (!dto.getDistrictId().equals(profile.getDistrictId())) {
            District district = districtRepository
                    .findById(dto.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.getDistrictId()));
            profile.setDistrictId(dto.getDistrictId());
            profile.setDistrictName(district.getName());
        }
        profile.setPosition(dto.getPosition());
        profileRepository.save(profile);
    }
}
