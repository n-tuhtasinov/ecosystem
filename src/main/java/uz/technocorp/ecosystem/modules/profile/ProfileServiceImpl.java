package uz.technocorp.ecosystem.modules.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
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

    private final ProfileSpecification profileSpecification;
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

    @Override
    public Integer getOfficeId(UUID profileId) {
        return profileRepository.findById(profileId).map(Profile::getOfficeId).orElseThrow(() -> new ResourceNotFoundException("OfficeID topilmadi"));
    }

    @Override
    public Long getProfileTin(UUID profileId) {
        return profileRepository.findById(profileId).map(Profile::getTin).orElseThrow(
                () -> new ResourceNotFoundException("Siz tashkilot sifatida tizimda mavjud emassiz (INN biriktirilmagan)"));
    }

    @Override
    public Page<ProfileView> getProfilesForPrevention(Integer inspectorOfficeId, PreventionParamsDto params) {
        // Query
        Specification<Profile> hasQuery = (root, cq, cb) -> {
            if (params.getSearch() == null || params.getSearch().isBlank()) {
                return cb.conjunction();
            }
            Long tin = parseTin(params.getSearch());
            return tin != null
                    ? cb.equal(root.get("tin"), tin)
                    : cb.like(cb.lower(root.get("legalName")), "%" + params.getSearch().toLowerCase() + "%");
        };
        Specification<Profile> spec = Specification
                .where(profileSpecification.notInPreventionForYear(inspectorOfficeId, LocalDate.now().getYear()))
                .and(hasQuery);

        Sort sort = Sort.by(Sort.Direction.ASC, "legalName");
        PageRequest pageRequest = PageRequest.of(params.getPage() - 1, params.getSize(), sort);

        Page<Profile> profiles = profileRepository.findAll(spec, pageRequest);
        List<ProfileView> list = profiles.stream().map(this::map).toList();

        return new PageImpl<>(list, pageRequest, profiles.getTotalElements());
    }

    private void setRegion(Integer regionId, Profile profile) {
        if (regionId == null) {
            profile.setRegionId(null);
            profile.setRegionName(null);
            return;
        }

        if (!regionId.equals(profile.getRegionId())) {
            Region region = regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", regionId));
            profile.setRegionId(regionId);
            profile.setRegionName(region.getName());
        }
    }

    private void setDistrict(Integer districtId, Profile profile) {
        if (districtId == null) {
            profile.setDistrictId(null);
            profile.setDistrictName(null);
            return;
        }

        if (!districtId.equals(profile.getDistrictId())) {
            District district = districtRepository.findById(districtId).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", districtId));
            profile.setDistrictId(districtId);
            profile.setDistrictName(district.getName());
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

    private Long parseTin(String query) {
        try {
            return query.length() == 9 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    // MAPPER
    private ProfileView map(Profile profile) {
        ProfileView view = new ProfileView();
        view.setId(profile.getId().toString());
        view.setTin(profile.getTin());
        view.setLegalName(profile.getLegalName());
        view.setLegalAddress(profile.getLegalAddress());
        view.setPin(profile.getPin());
        view.setDistrictName(profile.getDistrictName());

        return view;
    }
}
