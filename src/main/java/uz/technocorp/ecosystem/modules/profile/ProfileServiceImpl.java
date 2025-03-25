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

        //get region
        Region region = getRegion(dto.getRegionId());

        //get district
        District district = getDistrict(dto.getDistrictId());

        Profile saved = profileRepository.save(Profile.builder()

                .tin(dto.getTin())
                .legalName(dto.getLegalName())
                .legalAddress(dto.getLegalAddress())
                .fullName(dto.getFullName())
                .pin(dto.getPin())
                .departmentId(dto.getDepartmentId())
                .officeId(dto.getOfficeId())
                .regionId(dto.getRegionId())
                .regionName(region != null ? region.getName() : null)
                .districtId(dto.getDistrictId())
                .districtName(district != null ? district.getName() : null)
                .position(dto.getPosition())
                .phoneNumber(dto.getPhoneNumber())
                .legalOwnershipType(dto.getLegalOwnershipType())
                .legalForm(dto.getLegalForm())
                .build());

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
        setRegion(dto.getRegionId(), profile); //set region
        setDistrict(dto.getDistrictId(), profile); //set district
        profile.setPosition(dto.getPosition());
        profileRepository.save(profile);
    }

    private void setRegion(Integer regionId, Profile profile) {
        if (!regionId.equals(profile.getRegionId())) {
            Region region = getRegion(regionId);
            profile.setRegionId(regionId);
            profile.setRegionName(region!=null? region.getName():null);
        }
    }

    private void setDistrict(Integer districtId, Profile profile) {
        if (!districtId.equals(profile.getDistrictId())) {
            District district = getDistrict(districtId);
            profile.setDistrictId(districtId);
            profile.setDistrictName(district!=null? district.getName():null);
        }
    }

    private Region getRegion(Integer regionId) {
        Region region = null;
        if (regionId != null) {
            region = regionRepository
                    .findById(regionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", regionId));
        }
        return region;
    }

    private District getDistrict(Integer districtId) {
        District district = null;
        if (districtId != null) {
            district = districtRepository
                    .findById(districtId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", districtId));
        }
        return district;
    }
}
