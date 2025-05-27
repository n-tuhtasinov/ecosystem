package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 20.05.2025
 * @since v1.0
 */
@Component
public class PreventionSpecification {

    // Search
    public Specification<Prevention> hasSearch(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            Long tin = parseTin(search);
            return tin != null
                    ? cb.equal(root.get("profileTin"), tin)
                    : cb.like(cb.lower(root.get("profileName")), "%" + search.toLowerCase() + "%");
        };
    }

    // Date range
    public Specification<Prevention> hasDateRange(PreventionParamsDto params) {
        return (root, query, cb) -> {
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
    }

    public Specification<Prevention> hasInspectorId(UUID inspectorId) {
        return (root, cq, cb)
                -> inspectorId == null ? cb.conjunction() : cb.equal(root.get("createdBy"), inspectorId);
    }

    public Specification<Prevention> hasRegionId(Integer regionId) {
        return (root, cq, cb)
                -> regionId == null ? cb.conjunction() : cb.equal(root.get("regionId"), regionId);
    }

    public Specification<Prevention> hasDistrictId(Integer districtId) {
        return (root, cq, cb)
                -> districtId == null ? cb.conjunction() : cb.equal(root.get("districtId"), districtId);
    }

    public Specification<Prevention> hasViewed(Boolean viewed) {
        return (root, query, cb)
                -> viewed != null ? cb.equal(root.get("viewed"), viewed) : null;
    }

    private Long parseTin(String query) {
        try {
            return query.length() == 9 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
