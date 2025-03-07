package uz.technocorp.ecosystem.modules.appeal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealSearchCriteria;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class AppealCustomRepositoryImpl implements AppealCustomRepository {

    private EntityManager em;
    @Override
    public Page<AppealCustom> appealCustoms(Pageable pageable, AppealSearchCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppealCustom> cq = cb.createQuery(AppealCustom.class);
        Root<Appeal> appeal = cq.from(Appeal.class);
        List<Predicate> predicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish
        if (criteria.getStatus()!= null && !criteria.getStatus().isEmpty()) {
            predicates.add(cb.equal(appeal.get("status"), AppealStatus.valueOf(criteria.getStatus())));
        }
        if (criteria.getAppealType()!= null && !criteria.getAppealType().isEmpty()) {
            predicates.add(cb.equal(appeal.get("appealType"), AppealType.valueOf(criteria.getAppealType())));
        }
        if (criteria.getLegalTin()!= null && !criteria.getLegalTin().isEmpty()) {
            predicates.add(cb.equal(appeal.get("legalTin"), criteria.getLegalTin()));
        }

        if (criteria.getDate() != null && !criteria.getDate().isEmpty()) {
            predicates.add(cb.equal(appeal.get("date"), criteria.getDate()));
        }

        if (criteria.getOfficeId() != null) {
            predicates.add(cb.equal(appeal.get("officeId"), criteria.getOfficeId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        // DTO yaratish
        cq.select(cb
                .construct(
                        AppealCustom.class,
                        appeal.get("id"),
                        appeal.get("date"),
                        appeal.get("status"),
                        appeal.get("legalTin"),
                        appeal.get("legalName"),
                        appeal.get("regionName"),
                        appeal.get("districtName"),
                        appeal.get("mainId"),
                        appeal.get("address"),
                        appeal.get("email"),
                        appeal.get("phoneNumber"),
                        appeal.get("appealType"),
                        appeal.get("inspectorName"),
                        appeal.get("deadline"),
                        appeal.get("officeName")
                ));
        // Qidiruvni amalga oshirish
        TypedQuery<AppealCustom> query = em.createQuery(cq);
        // Pagination uchun sahifani sozlash
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Umumiy natijalar sonini olish
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Appeal> countRoot = countQuery.from(Appeal.class);

//        if (!predicates.isEmpty()) {
//            countQuery.where(predicates.toArray(new Predicate[0]));
//        }

        countQuery.select(cb.count(countRoot));
        Long totalElements = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, totalElements);
    }
}
