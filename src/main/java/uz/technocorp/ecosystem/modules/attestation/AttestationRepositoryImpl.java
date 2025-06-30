package uz.technocorp.ecosystem.modules.attestation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AttestationRepositoryImpl implements CustomAttestationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<UUID> findDistinctAppealIds(Specification<Attestation> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UUID> query = cb.createQuery(UUID.class);
        Root<Attestation> root = query.from(Attestation.class);

        // SELECT appealId ...
        query.select(root.get("appealId"));

        // Barcha filterlarni qo'llash
        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        // ... GROUP BY appealId
        query.groupBy(root.get("appealId"));

        // Saralashni qo'llash
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = sort.stream()
                    .map(order -> {
                        Expression<LocalDateTime> dateExpression = root.get(order.getProperty()).as(LocalDateTime.class);
                        return order.isAscending() ? cb.asc(cb.greatest(dateExpression)) : cb.desc(cb.greatest(dateExpression));
                    })
                    .toList();
            query.orderBy(orders);
        }

        // Pagination va so'rovni bajarish
        TypedQuery<UUID> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<UUID> content = typedQuery.getResultList();

        long total = executeCountQuery(spec);

        return new PageImpl<>(content, pageable, total);
    }

    private long executeCountQuery(Specification<Attestation> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Attestation> root = countQuery.from(Attestation.class);

        Predicate predicate = spec.toPredicate(root, countQuery, cb);
        countQuery.where(predicate);

        countQuery.select(cb.countDistinct(root.get("appealId")));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}