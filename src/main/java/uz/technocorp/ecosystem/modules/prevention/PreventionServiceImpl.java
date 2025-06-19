package uz.technocorp.ecosystem.modules.prevention;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.enums.PreventionType;
import uz.technocorp.ecosystem.modules.prevention.file.PreventionFile;
import uz.technocorp.ecosystem.modules.prevention.file.PreventionFileService;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionTypeView;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class PreventionServiceImpl implements PreventionService {

    private final RegionService regionService;
    private final DistrictService districtService;
    private final ProfileService profileService;
    private final AttachmentService attachmentService;
    private final PreventionSpecification specification;
    private final PreventionFileService preventionFileService;
    private final PreventionRepository repository;

    @Override
    public Page<?> getAll(User user, PreventionParamsDto params) {
        switch (user.getRole()) {
            case Role.CHAIRMAN, Role.HEAD, Role.MANAGER -> {
                return params.getIsPassed()
                        ? getAllPassedForCommittee(params)
                        : getAllWithoutPassedForCommittee(params);
            }
            case Role.REGIONAL -> {
                return params.getIsPassed()
                        ? getAllPassedForRegional(user, params)
                        : getAllWithoutPassedForRegional(user, params);
            }
            case Role.INSPECTOR -> {
                return params.getIsPassed()
                        ? getAllPassedByInspector(user, params)
                        : getAllWithoutPassedForInspector(user, params);
            }
            case Role.LEGAL -> {
                return getAllByCitizen(user);
            }
            default -> {
                return Page.empty();
            }
        }
    }

    @Override
    public PreventionView getById(User user, UUID preventionId) {
        switch (user.getRole()) {
            case Role.CHAIRMAN, Role.HEAD, Role.MANAGER -> {
                return getByIdForCommittee(preventionId);
            }
            case Role.REGIONAL -> {
                return getByIdForRegional(user, preventionId);
            }
            case Role.INSPECTOR -> {
                return getByIdForInspector(user, preventionId);
            }
            case Role.LEGAL -> {
                return getByIdForCitizen(user, preventionId);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public List<PreventionTypeView> getTypes() {
        return Arrays.stream(PreventionType.values()).map(this::map).toList();
    }

    @Override
    public Page<PreventionView> getAllPassedForCommittee(PreventionParamsDto params) {
        return getFilteredPreventions(params, null, null);
    }

    @Override
    public Page<ProfileView> getAllWithoutPassedForCommittee(PreventionParamsDto params) {
        return profileService.getProfilesForPrevention(params);
    }

    @Override
    public PreventionView getByIdForCommittee(UUID preventionId) {
        return repository.findById(preventionId).map(this::map).orElseThrow(() -> new ResourceNotFoundException("Profilaktika", "ID", preventionId));
    }

    @Override
    public Page<PreventionView> getAllPassedForRegional(User user, PreventionParamsDto params) {
        Profile profile = profileService.getProfile(user.getProfileId());
        return getFilteredPreventions(params, profile.getRegionId(), null);
    }

    @Override
    public Page<ProfileView> getAllWithoutPassedForRegional(User user, PreventionParamsDto params) {
        // Get regional regionId by user profileId and set it to params
        params.setRegionId(profileService.getProfile(user.getProfileId()).getRegionId());

        return profileService.getProfilesForPrevention(params);
    }

    @Override
    public PreventionView getByIdForRegional(User user, UUID preventionId) {
        // Get user regionId
        Integer regionId = profileService.getProfile(user.getProfileId()).getRegionId();

        Optional<Prevention> prevOpl = repository.findByIdAndRegionId(preventionId, regionId);
        return prevOpl.map(this::map).orElseThrow(() -> new ResourceNotFoundException("Sizning viloyatingizda bunday profilaktika topilmadi"));
    }

    @Override
    public Page<PreventionView> getAllPassedByInspector(User user, PreventionParamsDto params) {
        return getFilteredPreventions(params, null, user.getId());
    }

    @Override
    public Page<ProfileView> getAllWithoutPassedForInspector(User user, PreventionParamsDto params) {
        // Get inspector regionId by userProfileId and set it to params
        params.setRegionId(profileService.getProfile(user.getProfileId()).getRegionId());

        return profileService.getProfilesForPrevention(params);
    }

    @Override
    public PreventionView getByIdForInspector(User user, UUID preventionId) {
        Optional<Prevention> prevOpl = repository.findByIdAndCreatedBy(preventionId, user.getId());
        return prevOpl.map(this::map).orElseThrow(() -> new ResourceNotFoundException("Profilaktika", "ID", preventionId));
    }

    @Override
    @Transactional
    public void create(User user, PreventionDto dto) {
        // Check and get a profile by tin
        Profile profile = getProfileByTin(dto.getTin());

        Integer currentYear = LocalDate.now().getYear();
        String preventionFile = checkPreventionFile(currentYear, profile.getRegionId());

        // Check prevention
        Prevention oldPrevention = getPreventionByProfileTinAndYear(profile.getTin(), currentYear);
        if (oldPrevention != null) {
            throw new ResourceNotFoundException("Bu tashkilotga (STIR : " + profile.getTin() + ") "
                    + currentYear + " yil hisobi bo'yicha profilaktika o'tilgan");
        }

        Prevention prevention = new Prevention();

        prevention.setTypeId(dto.getTypeId());
        prevention.setContent(dto.getContent());
        prevention.setYear(currentYear);
        prevention.setPreventionFilePath(preventionFile);
        prevention.setEventFilePath(dto.getEventFile());
        prevention.setOrganizationFilePath(dto.getOrganizationFile());
        prevention.setViewed(false);
        prevention.setViewDate(null);
        prevention.setInspectorName(user.getName());
        prevention.setProfileTin(profile.getTin());
        prevention.setLegalName(profile.getLegalName());
        prevention.setLegalAddress(profile.getLegalAddress());
        prevention.setRegionId(profile.getRegionId());
        prevention.setDistrictId(profile.getDistrictId());

        repository.save(prevention);

        // Delete files from attachment
        List<String> filePaths = Stream.of(dto.getEventFile(), dto.getOrganizationFile()).filter(Objects::nonNull).toList();
        if (!filePaths.isEmpty()) {
            attachmentService.deleteByPaths(List.of(dto.getEventFile(), dto.getOrganizationFile()));
        }
    }

    @Override
    public void deleteById(User user, UUID preventionId) {
        Optional<Prevention> prevOpl = repository.getForDelete(preventionId, user.getId(), LocalDateTime.now().minusDays(3));
        if (prevOpl.isEmpty()) {
            throw new ResourceNotFoundException("Profilaktika o'tkazilganiga 3 kundan oshmagan va tashkilot uni ko'rmagan bo'lishi kerak");
        }
        repository.delete(prevOpl.get());
    }

    @Override
    public Page<PreventionView> getAllByCitizen(User user) {
        Long tin = getProfileTinById(user.getProfileId());

        List<Prevention> preventionList = repository.findAllByProfileTinOrderByYearDesc(tin);
        if (preventionList == null || preventionList.isEmpty()) {
            return Page.empty();
        }
        return new PageImpl<>(preventionList.stream().map(this::map).toList());
    }

    @Override
    public PreventionView getByIdForCitizen(User user, UUID preventionId) {
        Long profileTin = getProfileTinById(user.getProfileId());

        Optional<Prevention> prevOpl = repository.findByIdAndProfileTin(preventionId, profileTin);
        if (prevOpl.isEmpty()) {
            throw new ResourceNotFoundException("Profilaktika", "ID", preventionId);
        }
        Prevention prevention = prevOpl.get();
        if (!prevention.getViewed()) {
            repository.updateView(prevention.getId(), LocalDateTime.now());
        }
        return map(prevention);
    }

    private Page<PreventionView> getFilteredPreventions(PreventionParamsDto params, Integer fixedRegionId, UUID fixedInspectorId) {
        Specification<Prevention> combinedSpec = buildSpecification(params, fixedRegionId, fixedInspectorId);

        // Create PageRequest with Sort by createdAt desc
        PageRequest pageRequest = getPageRequest(params);

        // Get Preventions from DB
        Page<Prevention> preventions = repository.findAll(combinedSpec, pageRequest);

        // Create PageImpl
        return new PageImpl<>(preventions.stream().map(this::map).toList(), pageRequest, preventions.getTotalElements());
    }

    private Specification<Prevention> buildSpecification(PreventionParamsDto params, Integer fixedRegionId, UUID fixedInspectorId) {
        return Specification
                .where(specification.hasSearch(params.getSearch()))
                .and(specification.hasRegionId(getRegionId(params, fixedRegionId)))
                .and(specification.hasDistrictId(params.getDistrictId()))
                .and(specification.hasInspectorId(getInspectorId(params, fixedInspectorId)))
                .and(specification.hasViewed(params.getViewed()))
                .and(specification.hasDateRange(params));
    }

    private Integer getRegionId(PreventionParamsDto params, Integer fixedRegionId) {
        return Optional.ofNullable(fixedRegionId).orElse(params.getRegionId());
    }

    private UUID getInspectorId(PreventionParamsDto params, UUID fixedInspectorId) {
        return Optional.ofNullable(fixedInspectorId).orElse(params.getInspectorId());
    }

    private Profile getProfileByTin(Long tin) {
        return profileService.findByTin(tin);
    }

    private Prevention getPreventionByProfileTinAndYear(Long tin, Integer year) {
        return repository.findByProfileTinAndYear(tin, year).orElse(null);
    }

    private Long getProfileTinById(UUID profileId) {
        return profileService.getProfileTin(profileId);
    }

    private PageRequest getPageRequest(PreventionParamsDto params) {
        return PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    private String checkPreventionFile(Integer year, Integer regionId) {
        PreventionFile file = preventionFileService.findByYearAndRegion(year, regionId);
        if (file == null) {
            throw new CustomException("Profilaktika yaratish uchun Hududiy bo'lim tomonidan Tadbir rejasi fayli kiritilmagan");
        }
        return file.getPath();
    }

    // MAPPER
    private PreventionView map(Prevention prevention) {
        PreventionView dto = new PreventionView();

        dto.setId(prevention.getId().toString());
        dto.setType(new PreventionTypeView(prevention.getTypeId(), PreventionType.find(prevention.getTypeId()).getName()));
        dto.setContent(prevention.getContent());
        dto.setYear(prevention.getYear());
        dto.setViewed(prevention.getViewed());
        dto.setViewDate(prevention.getViewDate());
        dto.setCreatedAt(prevention.getCreatedAt());
        dto.setCreatedBy(prevention.getCreatedBy().toString());
        dto.setInspectorName(prevention.getInspectorName());
        dto.setTin(prevention.getProfileTin());
        dto.setLegalName(prevention.getLegalName());
        dto.setLegalAddress(prevention.getLegalAddress());
        dto.setRegionName(regionService.findById(prevention.getRegionId()).getName());
        dto.setDistrictName(districtService.getDistrict(prevention.getDistrictId()).getName());
        dto.setPreventionFilePath(prevention.getPreventionFilePath());
        dto.setEventFilePath(prevention.getEventFilePath());
        dto.setOrganizationFilePath(prevention.getOrganizationFilePath());

        return dto;
    }

    private PreventionTypeView map(PreventionType type) {
        PreventionTypeView view = new PreventionTypeView();
        view.setId(type.getId());
        view.setName(type.getName());
        return view;
    }
}
