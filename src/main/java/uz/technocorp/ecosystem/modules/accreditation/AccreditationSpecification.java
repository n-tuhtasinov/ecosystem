package uz.technocorp.ecosystem.modules.accreditation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;

/**
 * @author Suxrob
 * @version 1.0
 * @created 11.07.2025
 * @since v1.0
 */
@Component
public class AccreditationSpecification {

    // Search
    public Specification<Accreditation> search(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            Long tin = parseTin(search);
            return tin != null
                    ? cb.equal(root.get("tin"), tin)
                    : cb.like(cb.lower(root.get("legalName")), "%" + search.toLowerCase() + "%");
        };
    }

    // Type
    public Specification<Accreditation> type(AccreditationType type) {
        return (root, cq, cb) -> cb.equal(root.get("type"), type);
    }

    private Long parseTin(String search) {
        try {
            return search.length() == 9 ? Long.parseLong(search) : null;
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
