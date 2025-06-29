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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UUID> query = cb.createQuery(UUID.class);
        Root<Attestation> root = query.from(Attestation.class);

        // query.select(root.get("appealId")).distinct(true); // <--- BU QATOR O'ZGARTIRILADI
        query.select(root.get("appealId")); // <-- distinct(true) OLIB TASHLANDI

        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = sort.stream()
                    .map(order -> order.isAscending() ? cb.asc(root.get(order.getProperty())) : cb.desc(root.get(order.getProperty())))
                    .toList();
            query.orderBy(orders);
        }

        TypedQuery<UUID> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<UUID> content = typedQuery.getResultList();

        // COUNT so'rovi ham o'zgarishi kerak!
        long total = executeCountQueryForLatest(spec);

        return new PageImpl<>(content, pageable, total);
    }

    // Jami sonni hisoblash uchun ham yangi metod kerak
    private long executeCountQueryForLatest(Specification<Attestation> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Attestation> root = countQuery.from(Attestation.class);

        // Asosiy shart (Specification) va eng oxirgi yozuv shartini qo'llaymiz
        Predicate predicate = spec.toPredicate(root, countQuery, cb);
        countQuery.where(predicate);

        // Endi appealId'lar sonini sanaymiz.
        countQuery.select(cb.count(root)); // Bu yerda distinct kerak emas, chunki har bir appealId'dan faqat bittasi qolgan.

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}