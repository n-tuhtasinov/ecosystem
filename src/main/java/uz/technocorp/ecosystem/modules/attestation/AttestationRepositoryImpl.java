package uz.technocorp.ecosystem.modules.attestation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class AttestationRepositoryImpl implements CustomAttestationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<UUID> findDistinctAppealIds(Specification<Attestation> spec, Pageable pageable) {
        // 1. MA'LUMOTLAR UCHUN ASOSIY SO'ROV
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UUID> query = cb.createQuery(UUID.class);
        Root<Attestation> root = query.from(Attestation.class);

        query.select(root.get("appealId")).distinct(true);

        // Specification'dan filterlarni (WHERE shartlarini) qo'llash
        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        // Sorting (saralash)ni qo'llash
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            // Sort obyektidan Criteria API Order obyektlarini yaratamiz
            List<jakarta.persistence.criteria.Order> orders = sort.stream()
                    .map(order -> order.isAscending() ? cb.asc(root.get(order.getProperty())) : cb.desc(root.get(order.getProperty())))
                    .toList();
            query.orderBy(orders);
        }

        // So'rovni yaratish va pagination'ni qo'llash
        TypedQuery<UUID> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset()); // Qaysi elementdan boshlab olish
        typedQuery.setMaxResults(pageable.getPageSize());     // Nechta element olish

        List<UUID> content = typedQuery.getResultList();

        // 2. JAMI ELEMENTLAR SONINI OLISH UCHUN COUNT SO'ROVI
        long total = executeCountQuery(spec);

        // 3. Page obyektini yaratish va qaytarish
        return new PageImpl<>(content, pageable, total);
    }

    private long executeCountQuery(Specification<Attestation> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Attestation> countRoot = countQuery.from(Attestation.class);

        // Filterlarni count so'roviga ham qo'llaymiz
        Predicate predicate = spec.toPredicate(countRoot, countQuery, cb);
        countQuery.where(predicate);

        // COUNT(DISTINCT appealId) so'rovini yaratamiz
        countQuery.select(cb.countDistinct(countRoot.get("appealId")));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}