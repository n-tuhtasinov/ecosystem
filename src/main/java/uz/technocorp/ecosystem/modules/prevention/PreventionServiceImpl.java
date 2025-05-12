package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.prevention.dto.PagingDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
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
    private final ProfileRepository profileRepository;

    @Override
    public PagingDto<PreventionDto> getOrganizationsWithEvents(User user, PreventionParamsDto params) {
        List<Sort.Order> orders = List.of(Sort.Order.desc("createdAt"));

        // Query
        Specification<Prevention> hasQuery = (root, cq, cb) -> {
            if (params.getQuery() != null && !params.getQuery().isBlank()) {
                Long tin = parseLong(params.getQuery());
                if (tin != null) {
                    return cb.equal(root.get("profileTin"), tin);
                } else {
                    cb.like(root.get("type"), "%" + params.getQuery() + "%");
                }
            }
            return null;
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
        Specification<Prevention> hasViewed = (root, query, cb) -> {
            if (params.getViewed() != null) {
                return cb.equal(root.get("viewed"), params.getViewed());
            }
            return null;
        };

        // Get Prevention
        Page<Prevention> transactions = preventionRepository.findAll(
                where(hasQuery).and(hasDateRange).and(hasViewed), PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(orders)));

        // Create paging dto
        PagingDto<PreventionDto> paging = new PagingDto<>((int) transactions.getTotalElements(), params.getPage(), params.getSize());
        paging.getItems().addAll(transactions.stream().map(this::map).toList());

        return paging;
    }

    @Override
    public PagingDto<PreventionDto> getOrganizationsWithoutEvents(User user, PreventionParamsDto params) {
        /**
         * query da INN yoki tashkilot name kelishi mumkin
         * page va size keladi
         * inspector officeID organization officeID ga teng bo'lganlar va prevention qilinmaganlarni yuborish kerak
         * Kerakli fieldlar: Organization(name,tin,address)
         */

        // Get inspector officeId by userProfileId
        Integer inspectorOfficeId = profileRepository.findById(user.getProfileId()).map(Profile::getOfficeId).orElseThrow(() -> new ResourceNotFoundException("Inspektor hududga biriktirilmagan"));
        Integer currentYear = LocalDate.now().getYear();


        /**
         * query yozish kerak
         * & tin bor bo'lishi kerak
         * & inspectorOfficeId = profileOfficeId
         * & prevention table da currentYear da bo'lmasligi kerak
         */

        return null;
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

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // MAPPER
    private PreventionDto map(Prevention prevention) {
        return null;
    }
}
