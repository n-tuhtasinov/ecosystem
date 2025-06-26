package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author Suxrob
 * @version 1.0
 * @created 25.06.2025
 * @since v1.0
 */
@Component
public class AttestationSpecification {

    // Search
    public Specification<Attestation> hasSearch(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            Long pin = parsePin(search);
            return pin != null
                    ? cb.equal(root.get("employeePin"), pin)
                    : cb.like(cb.lower(root.get("employeeName")), "%" + search.toLowerCase() + "%");
        };
    }

    public Specification<Attestation> hasLegalTin(Long tin) {
        return (root, cq, cb)
                -> tin == null ? cb.conjunction() : cb.equal(root.get("legalTin"), tin);
    }

    private Long parsePin(String query) {
        try {
            return query.length() == 14 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
