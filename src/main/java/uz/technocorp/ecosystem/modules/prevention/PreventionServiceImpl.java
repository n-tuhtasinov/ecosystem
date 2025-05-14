package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.prevention.dto.PagingDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private final PreventionRepository preventionRepository;
    private final ProfileService profileService;

    @Override
    public PagingDto<PreventionView> getOrganizationsWithEventsByInspector(User user, PreventionParamsDto params) {
        List<Sort.Order> orders = List.of(Sort.Order.desc("createdAt"));

        // Query
        Specification<Prevention> hasQuery = (root, cq, cb) -> {
            if (params.getQuery() == null || params.getQuery().isBlank()) {
                return null;
            }
            Long tin = parseTin(params.getQuery());
            return tin != null
                    ? cb.equal(root.get("profileTin"), tin)
                    : cb.like(root.get("profileName"), "%" + params.getQuery() + "%");
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

        // Get Prevention
        Page<Prevention> transactions = preventionRepository.findAll(
                where(hasQuery).and(hasDateRange).and(hasViewed), PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(orders)));

        // Create paging dto
        PagingDto<PreventionView> paging = new PagingDto<>((int) transactions.getTotalElements(), params.getPage(), params.getSize());
        paging.getItems().addAll(transactions.stream().map(this::map).toList());

        return paging;
    }

    @Override
    public PagingDto<ProfileView> getOrganizationsWithoutEvents(User user, PreventionParamsDto params) {
        // Get inspector officeId by userProfileId
        Integer inspectorOfficeId = profileService.getOfficeId(user.getProfileId());

        return profileService.getProfilesForPrevention(inspectorOfficeId, params);
    }

    @Override
    public void create(User user, PreventionDto dto) {
        preventionRepository.save(Prevention.builder()
                .type(dto.getType())
                .content(dto.getContent())
                .year(LocalDate.now().getYear())
                .files(dto.getFilePaths())
                .profileTin(dto.getTin())
                .build());
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
        PreventionView view = new PreventionView();

        view.setId(prevention.getId().toString());
        view.setType(prevention.getType());
        view.setContent(prevention.getContent());
        view.setYear(prevention.getYear());
        view.setViewDate(prevention.getViewDate());
        view.setCreatedAt(prevention.getCreatedAt());
        view.setCreatedBy(prevention.getCreatedBy().toString());
        view.setInspectorName(prevention.getInspectorName());
        view.setProfileTin(prevention.getProfileTin());
        view.setProfileName(prevention.getProfileName());
        view.setProfileAddress(prevention.getProfileAddress());

        return view;
    }
}
