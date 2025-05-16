package uz.technocorp.ecosystem.modules.appeal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class AppealRepoImpl implements AppealRepo {

    private final EntityManager em;

    @Override
    public Page<AppealCustom> getAppealCustoms(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "createdAt");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppealCustom> cq = cb.createQuery(AppealCustom.class);
        Root<Appeal> appeal = cq.from(Appeal.class);
        List<Predicate> predicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish
        if (params.get("status")!= null && !params.get("status").isEmpty()) {
            predicates.add(cb.equal(appeal.get("status"), AppealStatus.valueOf(params.get("status"))));
        }
        if (params.get("appealType") != null && !params.get("appealType").isEmpty()) {
            predicates.add(cb.equal(appeal.get("appealType"), AppealType.valueOf(params.get("params"))));
        }
        if (params.get("legalTin")!= null && !params.get("legalTin").isEmpty()) {
            predicates.add(cb.equal(appeal.get("legalTin"), params.get("legalTin")));
        }

        if (params.get("date") != null && !params.get("date").isEmpty()) {
//            predicates.add(cb.equal(appeal.get("date"), params.get("date")));
            predicates.add(cb.between(appeal.get("createdAt"), LocalDate.parse(params.get("startDate")), LocalDate.parse(params.get("endDate"))));
        }

        if (params.get("officeId") != null) {
            predicates.add(cb.equal(appeal.get("officeId"), params.get("officeId")));
        }

        if (params.get("executorId") != null) {
            predicates.add(cb.equal(appeal.get("executorId"), params.get("executorId")));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        // DTO yaratish
        cq.select(cb
                .construct(
                        AppealCustom.class,
                        appeal.get("id"),
                        appeal.get("createdAt"),
                        appeal.get("status"),
                        appeal.get("legalTin"),
                        appeal.get("number"),
                        appeal.get("legalName"),
                        appeal.get("regionName"),
                        appeal.get("districtName"),
                        appeal.get("address"),
                        appeal.get("phoneNumber"),
                        appeal.get("appealType"),
                        appeal.get("executorName"),
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
