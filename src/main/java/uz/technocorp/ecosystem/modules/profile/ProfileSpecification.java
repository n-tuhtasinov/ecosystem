package uz.technocorp.ecosystem.modules.profile;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.prevention.Prevention;

import java.util.Objects;

@Component
public class ProfileSpecification {

    public Specification<Profile> notInPreventionForYear(Long officeId, Integer year) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = Objects.requireNonNull(query).subquery(Long.class);
            Root<Prevention> preventionRoot = subquery.from(Prevention.class);
            subquery.select(preventionRoot.get("profileTin"))
                    .where(cb.equal(preventionRoot.get("year"), year));
            return cb.and(
                    cb.isNotNull(root.get("tin")),
                    cb.equal(root.get("officeId"), officeId),
                    cb.not(root.get("tin").in(subquery))
            );
        };
    }

    public Specification<Profile> tinContains(String tin) {
        return (root, query, cb) -> {
            if (tin == null || tin.isEmpty()) {
                return cb.conjunction(); // Always true predicate
            }
            return cb.like(root.get("tin").as(String.class), "%" + tin + "%");
        };
    }

    public Specification<Profile> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction(); // Always true predicate
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}