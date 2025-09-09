package uz.technocorp.ecosystem.modules.equipment;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

/**
 * @author Suxrob
 * @version 1.0
 * @created 08.09.2025
 * @since v1.0
 */
@Component
public class EquipmentSpecification {

    // Search
    public Specification<Equipment> hasSearch(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            Long identity = parseIdentity(search);
            return identity != null
                    ? cb.equal(root.get("ownerIdentity"), identity)
                    : cb.like(cb.lower(root.get("registryNumber")), "%" + search.toLowerCase() + "%");
        };
    }

    public Specification<Equipment> hasType(EquipmentType type) {
        return (root, cq, cb)
                -> type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    public Specification<Equipment> hasMode(String mode) {
        return (root, cq, cb)
                -> mode == null ? cb.conjunction() : cb.equal(root.get("mode"), mode);
    }

    public Specification<Equipment> hasRegionId(Integer regionId) {
        return (root, cq, cb)
                -> regionId == null ? cb.conjunction() : cb.equal(root.get("regionId"), regionId);
    }

    public Specification<Equipment> hasDistrictId(Integer districtId) {
        return (root, cq, cb)
                -> districtId == null ? cb.conjunction() : cb.equal(root.get("districtId"), districtId);
    }

    public Specification<Equipment> isActive(Boolean active) {
        return (root, cq, cb)
                -> active == null ? cb.conjunction() : cb.equal(root.get("isActive"), active);
    }

    public Specification<Equipment> fetch(String fetchColumn) {
        return (root, query, criteriaBuilder) -> {
            if (query == null) {
                return criteriaBuilder.conjunction();
            }
            Class<?> resultType = query.getResultType();
            if (resultType != null && resultType != Long.class && resultType != long.class) {
                boolean alreadyFetched = root.getFetches().stream()
                        .anyMatch(fetch -> fetch.getAttribute().getName().equals(fetchColumn));
                if (!alreadyFetched) {
                    root.fetch(fetchColumn, JoinType.LEFT);
                }
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Long parseIdentity(String query) {
        try {
            return (query.length() == 9 || query.length() == 14) ? Long.parseLong(query) : null;
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
