package uz.technocorp.ecosystem.modules.hf;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author Suxrob
 * @version 1.0
 * @created 05.09.2025
 * @since v1.0
 */
@Component
public class HfSpecification {

    // Search
    public Specification<HazardousFacility> hasSearch(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            Long tin = parseTin(search);
            return tin != null
                    ? cb.equal(root.get("legalTin"), tin)
                    : cb.like(cb.lower(root.get("registryNumber")), "%" + search.toLowerCase() + "%");
        };
    }

    public Specification<HazardousFacility> hasMode(String mode) {
        return (root, cq, cb)
                -> mode == null ? cb.conjunction() : cb.equal(root.get("mode"), mode);
    }

    public Specification<HazardousFacility> hasRegionId(Integer regionId) {
        return (root, cq, cb)
                -> regionId == null ? cb.conjunction() : cb.equal(root.get("regionId"), regionId);
    }

    public Specification<HazardousFacility> hasDistrictId(Integer districtId) {
        return (root, cq, cb)
                -> districtId == null ? cb.conjunction() : cb.equal(root.get("districtId"), districtId);
    }

    public Specification<HazardousFacility> fetchHfType() {
        return (root, query, criteriaBuilder) -> {
            if (query == null) {
                return criteriaBuilder.conjunction();
            }
            Class<?> resultType = query.getResultType();
            if (resultType != null && resultType != Long.class && resultType != long.class) {
                boolean alreadyFetched = root.getFetches().stream()
                        .anyMatch(fetch -> fetch.getAttribute().getName().equals("hfType"));
                if (!alreadyFetched) {
                    root.fetch("hfType", JoinType.LEFT);
                }
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Long parseTin(String query) {
        try {
            return query.length() == 9 ? Long.parseLong(query) : null;
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
