package uz.technocorp.ecosystem.modules.profile;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;
import uz.technocorp.ecosystem.modules.prevention.Prevention;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.dto.UserDto;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

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
    private final RegionService regionService;
    private final OfficeService officeService;

    @Override
    public UUID create(UserDto dto) {

        //get region and region name
        Region region = getRegion(dto.getRegionId());
        String regionName = region != null? region.getName() : null;

        //set region id and region name if the user has office id
        Integer regionId = dto.getRegionId();
        if (dto.getOfficeId()!=null){
            OfficeViewById byId = officeService.getById(dto.getOfficeId());
            Region innerRegion = regionService.findById(byId.getRegionId());
            regionId = innerRegion.getId();
            regionName = innerRegion.getName();
        }

        //get district
        District district = getDistrict(dto.getDistrictId());

        Profile saved = profileRepository.save(Profile.builder()

                .tin(dto.getTin())
                .legalName(dto.getLegalName())
                .legalAddress((region != null ? region.getName() + ", " : "") + (district != null ? district.getName() + ", " : "") + dto.getLegalAddress())
                .fullName(dto.getFullName())
                .pin(dto.getPin())
                .departmentId(dto.getDepartmentId())
                .officeId(dto.getOfficeId())
                .regionId(regionId)
                .regionName(regionName)
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
    public Profile findByTin(Long tin) {
        return profileRepository.findByTin(tin).orElseThrow(() -> new ResourceNotFoundException("Tashkilot", "STIR", tin));
    }

    @Override
    public Profile getProfile(UUID profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", profileId));
    }

    @Override
    public Page<ProfileView> getProfilesForPrevention(PreventionParamsDto params) {

        // Base condition
        Specification<Profile> baseQuery = (root, query, cb) -> {
            Subquery<Long> subquery = Objects.requireNonNull(query).subquery(Long.class);
            Root<Prevention> preventionRoot = subquery.from(Prevention.class);
            subquery.select(preventionRoot.get("profileTin"))
                    .where(cb.equal(preventionRoot.get("year"), params.getYear()));
            return cb.and(
                    cb.isNotNull(root.get("tin")),
                    cb.not(root.get("tin").in(subquery))
            );
        };

        // Search
        Specification<Profile> hasSearch = (root, cq, cb) -> {
            if (params.getSearch() == null || params.getSearch().isBlank()) {
                return cb.conjunction();
            }
            Long tin = parseTin(params.getSearch());
            return tin != null
                    ? cb.equal(root.get("tin"), tin)
                    : cb.like(cb.lower(root.get("legalName")), "%" + params.getSearch().toLowerCase() + "%");
        };

        // Region
        Specification<Profile> hasRegion = (root, cq, cb)
                -> params.getRegionId() == null ? cb.conjunction() : cb.equal(root.get("regionId"), params.getRegionId());

        // District
        Specification<Profile> hasDistrict = (root, cq, cb)
                -> params.getDistrictId() == null ? cb.conjunction() : cb.equal(root.get("districtId"), params.getDistrictId());

        // Create pageRequest with sort by legalName asc
        PageRequest pageRequest = PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(Sort.Direction.ASC, "legalName"));

        // Get Profiles
        Page<Profile> profiles = profileRepository.findAll(where(baseQuery).and(hasSearch).and(hasRegion).and(hasDistrict), pageRequest);

        // Create PageImpl
        return new PageImpl<>(profiles.stream().map(this::map).toList(), pageRequest, profiles.getTotalElements());
    }

    @Override
    public void addPhoneNumber(UUID profileId, String phoneNumber) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", profileId));
        if (profile.getPhoneNumber() == null) {
            profile.setPhoneNumber(phoneNumber);
            profileRepository.save(profile);
        }
    }

    @Override
    public ProfileInfoView getProfileInfo(Long tin) {
        return profileRepository
                .getProfileByTin(tin)
                .orElseThrow(() -> new ResourceNotFoundException("Tashkilot haqida ma'lumot", "STIR", tin));
    }

    @Override
    public boolean existsProfileByTin(Long tin) {
        return profileRepository.existsByTin(tin);
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
        if (regionId != null) {
            return regionRepository
                    .findById(regionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", regionId));
        }
        return null;
    }

    private District getDistrict(Integer districtId) {
        if (districtId != null) {
            return districtRepository
                    .findById(districtId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", districtId));
        }
        return null;
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
        view.setRegionName(profile.getRegionName());

        return view;
    }
}
