package uz.technocorp.ecosystem.modules.prevention;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionTypeView;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class PreventionServiceImpl implements PreventionService {

    private final ProfileService profileService;
    private final PreventionSpecification specification;
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

        // Search
        Specification<Prevention> hasSearch = specification.hasSearch(params.getSearch());

        // OfficeId
        Specification<Prevention> hasOfficeId = specification.hasOfficeId(params.getOfficeId());

        // InspectorId
        Specification<Prevention> hasInspectorId = specification.hasInspectorId(params.getInspectorId());

        // Viewed
        Specification<Prevention> hasViewed = specification.hasViewed(params.getViewed());

        // Date range
        Specification<Prevention> hasDateRange = specification.hasDateRange(params);

        // Create a page request with sort
        PageRequest pageRequest = getPageRequest(params);

        // Get Preventions
        Page<Prevention> preventions = repository.findAll(
                where(hasSearch).and(hasOfficeId).and(hasInspectorId).and(hasViewed).and(hasDateRange), pageRequest);

        // Create PageImpl
        return new PageImpl<>(preventions.stream().map(this::map).toList(), pageRequest, preventions.getTotalElements());
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
        // Get user profile office id
        Integer officeId = profileService.getOfficeId(user.getProfileId());

        // Search
        Specification<Prevention> hasSearch = specification.hasSearch(params.getSearch());

        // OfficeId
        Specification<Prevention> hasOfficeId = specification.hasOfficeId(officeId);

        // InspectorId
        Specification<Prevention> hasInspectorId = specification.hasInspectorId(params.getInspectorId());

        // Viewed
        Specification<Prevention> hasViewed = specification.hasViewed(params.getViewed());

        // Date range
        Specification<Prevention> hasDateRange = specification.hasDateRange(params);

        // Create a page request with sort
        PageRequest pageRequest = getPageRequest(params);

        // Get Prevention
        Page<Prevention> preventions = repository.findAll(
                where(hasSearch).and(hasOfficeId).and(hasInspectorId).and(hasViewed).and(hasDateRange), pageRequest);

        // Create paging dto
        return new PageImpl<>(preventions.stream().map(this::map).toList(), pageRequest, preventions.getTotalElements());
    }

    @Override
    public Page<ProfileView> getAllWithoutPassedForRegional(User user, PreventionParamsDto params) {
        // Get regional officeId by user profileId and set it to params
        params.setOfficeId(profileService.getOfficeId(user.getProfileId()));

        return profileService.getProfilesForPrevention(params);
    }

    @Override
    public PreventionView getByIdForRegional(User user, UUID preventionId) {
        // Get user profile office id
        Integer profileOfficeId = profileService.getOfficeId(user.getProfileId());

        Optional<Prevention> prevOpl = repository.findByIdAndOfficeId(preventionId, profileOfficeId);
        return prevOpl.map(this::map).orElseThrow(() -> new ResourceNotFoundException("Sizning hududiy bo'limingizda bunday profilaktika topilmadi"));
    }

    @Override
    public Page<PreventionView> getAllPassedByInspector(User user, PreventionParamsDto params) {
        // InspectorId
        Specification<Prevention> hasInspectorId = specification.hasInspectorId(user.getId());

        // Search
        Specification<Prevention> hasSearch = specification.hasSearch(params.getSearch());

        // Date range
        Specification<Prevention> hasDateRange = specification.hasDateRange(params);

        // Has viewed
        Specification<Prevention> hasViewed = specification.hasViewed(params.getViewed());

        // Create a page request with sort
        PageRequest pageRequest = getPageRequest(params);

        // Get Prevention
        Page<Prevention> preventions = repository.findAll(
                where(hasInspectorId).and(hasSearch).and(hasDateRange).and(hasViewed), pageRequest);

        // Create paging dto
        return new PageImpl<>(preventions.stream().map(this::map).toList(), pageRequest, preventions.getTotalElements());
    }

    @Override
    public Page<ProfileView> getAllWithoutPassedForInspector(User user, PreventionParamsDto params) {
        // Get inspector officeId by userProfileId and set it to params
        params.setOfficeId(profileService.getOfficeId(user.getProfileId()));

        return profileService.getProfilesForPrevention(params);
    }

    @Override
    public PreventionView getByIdForInspector(User user, UUID preventionId) {
        Optional<Prevention> prevOpl = repository.findByIdAndCreatedBy(preventionId, user.getId());
        return prevOpl.map(this::map).orElseThrow(() -> new ResourceNotFoundException("Profilaktika", "ID", preventionId));
    }

    @Override
    public void create(User user, PreventionDto dto) {
        // Check and get a profile by tin
        Profile profile = getProfileByTin(dto.getTin());

        Integer currentYear = LocalDate.now().getYear();

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
        prevention.setFiles(dto.getFilePaths());
        prevention.setViewed(false);
        prevention.setViewDate(null);
        prevention.setInspectorName(user.getName());
        prevention.setProfileTin(profile.getTin());
        prevention.setProfileName(profile.getLegalName());
        prevention.setProfileAddress(profile.getLegalAddress());
        prevention.setOfficeId(profile.getOfficeId());

        repository.save(prevention);
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
        dto.setProfileTin(prevention.getProfileTin());
        dto.setProfileName(prevention.getProfileName());
        dto.setProfileAddress(prevention.getProfileAddress());
        dto.setFiles(prevention.getFiles());

        return dto;
    }

    private PreventionTypeView map(PreventionType type) {
        PreventionTypeView view = new PreventionTypeView();
        view.setId(type.getId());
        view.setName(type.getName());
        return view;
    }
}
