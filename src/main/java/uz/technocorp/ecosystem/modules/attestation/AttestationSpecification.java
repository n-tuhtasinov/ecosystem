package uz.technocorp.ecosystem.modules.attestation;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.time.LocalDateTime;
import java.util.List;
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

    // LegalTin or LegalName
    public Specification<Attestation> hasLegal(String search) {
        return (root, cq, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            Long pin = parseTin(search);
            return pin != null
                    ? cb.equal(root.get("legalTin"), pin)
                    : cb.like(cb.lower(root.get("legalName")), "%" + search.toLowerCase() + "%");
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

    public Specification<Attestation> hasEmployeeLevels(List<EmployeeLevel> levels) {
        return (root, query, criteriaBuilder)
                -> (levels == null || levels.isEmpty()) ? criteriaBuilder.conjunction() : root.get("employeeLevel").in(levels);
    }

    public Specification<Attestation> hasRegionId(Integer regionId) {
        return (root, query, cb) -> regionId == null ? cb.conjunction() : cb.equal(root.get("regionId"), regionId);
    }

    public Specification<Attestation> hasExecutorId(UUID executorId) {
        return (root, query, cb) -> executorId == null ? cb.conjunction() : cb.equal(root.get("executorId"), executorId);
    }

    public Specification<Attestation> isLatest() {
        return (root, query, cb) -> {
            Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
            Root<Attestation> subRoot = subquery.from(Attestation.class);

            Expression<LocalDateTime> createdAtExpression = subRoot.get("createdAt").as(LocalDateTime.class);
            subquery.select(cb.greatest(createdAtExpression));
            subquery.where(cb.equal(subRoot.get("appealId"), root.get("appealId")));

            return cb.equal(root.get("createdAt"), subquery);
        };
    }

    private Long parsePin(String query) {
        try {
            return query.length() == 14 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Long parseTin(String query) {
        try {
            return query.length() == 9 ? Long.parseLong(query) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
