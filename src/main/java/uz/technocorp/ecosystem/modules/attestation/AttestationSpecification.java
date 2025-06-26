package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.util.UUID;

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

    public Specification<Attestation> hasStatus(AttestationStatus status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public Specification<Attestation> hasEmployeeLevel(EmployeeLevel level) {
        return (root, query, cb) -> level == null ? cb.conjunction() : cb.equal(root.get("employeeLevel"), level);
    }

    public Specification<Attestation> hasLegalTin(String legalTin) {
        return (root, query, cb) -> legalTin == null ? cb.conjunction() : cb.equal(root.get("legalTin"), legalTin);
    }

    public Specification<Attestation> legalNameContains(String legalName) {
        return (root, query, cb)
                -> (legalName == null || legalName.isEmpty())
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("legalName")), "%" + legalName.toLowerCase() + "%");
    }

    public Specification<Attestation> hfNameContains(String hfName) {
        return (root, query, cb)
                -> (hfName == null || hfName.isEmpty())
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("hfName")), "%" + hfName.toLowerCase() + "%");
    }

    public Specification<Attestation> hasRegionId(Integer regionId) {
        return (root, query, cb) -> regionId == null ? cb.conjunction() : cb.equal(root.get("regionId"), regionId);
    }

    public Specification<Attestation> hasExecutorId(UUID executorId) {
        return (root, query, cb) -> executorId == null ? cb.conjunction() : cb.equal(root.get("executorId"), executorId);
    }

    private Long parsePin(String query) {
        try {
            return query.length() == 14 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
