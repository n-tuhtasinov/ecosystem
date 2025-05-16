package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.criteria.Predicate;
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
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    private final PreventionRepository repository;
    private final ProfileService profileService;

    @Override
    public void create(User user, PreventionDto dto) {
        repository.save(Prevention.builder()
                .typeId(PreventionType.find(dto.getTypeId()).getId())
                .content(dto.getContent())
                .year(LocalDate.now().getYear())
                .files(dto.getFilePaths())
                .profileTin(dto.getTin())
                .viewed(false)
                .build());
    }

    @Override
    public Page<?> getAll(User user, PreventionParamsDto params) {
        switch (user.getRole()) {
            case Role.INSPECTOR -> {
                return params.getIsPassed()
                        ? getAllByInspectorAndPassed(user, params)
                        : getAllWithoutPassed(user, params);
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
    public PreventionView getByIdForInspector(User user, UUID preventionId) {
        Optional<Prevention> prevOpl = repository.findByIdAndCreatedBy(preventionId, user.getId());
        return prevOpl.map(this::map).orElseThrow(() -> new ResourceNotFoundException("Profilaktika", "ID", preventionId));
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
    public Page<PreventionView> getAllByInspectorAndPassed(User user, PreventionParamsDto params) {
        // Query
        Specification<Prevention> hasQuery = (root, cq, cb) -> {
            if (params.getSearch() == null || params.getSearch().isBlank()) {
                return null;
            }
            Long tin = parseTin(params.getSearch());
            return tin != null
                    ? cb.equal(root.get("profileTin"), tin)
                    : cb.like(root.get("profileName"), "%" + params.getSearch() + "%");
        };

        // Date range
        Specification<Prevention> hasDateRange = (root, query, cb) -> {
            // Optimize by creating a list of predicates and adding only the necessary ones
            List<Predicate> predicates = new ArrayList<>();
            if (params.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), params.getStartDate()));
            }
            if (params.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), params.getEndDate()));
            }
            // If no predicates, return default (all records match)
            return predicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(predicates.toArray(new Predicate[0]));
        };

        // Has viewed
        Specification<Prevention> hasViewed = (root, query, cb)
                -> params.getViewed() != null ? cb.equal(root.get("viewed"), params.getViewed()) : null;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(params.getPage() - 1, params.getSize(), sort);

        // Get Prevention
        Page<Prevention> preventions = repository.findAll(
                where(hasQuery).and(hasDateRange).and(hasViewed), pageRequest);

        // Create paging dto
        return new PageImpl<>(preventions.stream().map(this::map).toList(), pageRequest, preventions.getTotalElements());
    }

    @Override
    public Page<ProfileView> getAllWithoutPassed(User user, PreventionParamsDto params) {
        // Get inspector officeId by userProfileId
        Integer inspectorOfficeId = profileService.getOfficeId(user.getProfileId());

        return profileService.getProfilesForPrevention(inspectorOfficeId, params);
    }

    private Long getProfileTinById(UUID profileId) {
        return profileService.getProfileTin(profileId);
    }

    private Long parseTin(String query) {
        try {
            return query.length() == 9 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
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
