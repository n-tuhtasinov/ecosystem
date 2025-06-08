package uz.technocorp.ecosystem.modules.hf;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hftype.HfType;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.05.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class HfRepoImpl implements HfRepo {

    private final EntityManager em;

    @Override
    public Page<HfCustom> getHfCustoms(User user, HfParams params) {

        Pageable pageable= PageRequest.of(params.page()-1, params.size());

        // select uchun alohida query va root
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HfCustom> cq = cb.createQuery(HfCustom.class);
        Root<HazardousFacility> hfRoot = cq.from(HazardousFacility.class);
        List<Predicate> predicates = new ArrayList<>();
//
//        // JOIN — Region bilan LEFT JOIN qilish
//        Join<HazardousFacility, Region> regionJoin = hfRoot.join("region", JoinType.LEFT);
//
//        // JOIN — District bilan LEFT JOIN qilish
//        Join<HazardousFacility, District> districtJoin = hfRoot.join("district", JoinType.LEFT);

        // JOIN — Region bilan LEFT JOIN qilish
        Join<HazardousFacility, HfType> hfTypeJoin = hfRoot.join("hfType", JoinType.LEFT);

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<HazardousFacility> countRoot = countQuery.from(HazardousFacility.class);
        List<Predicate> countPredicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish
        if (params.legalTin() != null) {
            predicates.add(cb.equal(hfRoot.get("legalTin"), params.legalTin()));
            countPredicates.add(cb.equal(countRoot.get("legalTin"), params.legalTin()));
        }
        if (params.registryNumber() != null) {
            predicates.add(cb.equal(hfRoot.get("registryNumber"), params.registryNumber()));
            countPredicates.add(cb.equal(countRoot.get("registryNumber"), params.registryNumber()));
        }
        if (params.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(hfRoot.get("registrationDate"), params.startDate()));
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("registrationDate"), params.startDate()));
        }

        if (params.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(hfRoot.get("registrationDate"), params.endDate()));
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("registrationDate"), params.endDate()));
        }

        if (params.regionId() != null) {
            predicates.add(cb.equal(hfRoot.get("regionId"), params.regionId()));
            countPredicates.add(cb.equal(countRoot.get("regionId"), params.regionId()));
        }

        if (params.districtId() != null) {
            predicates.add(cb.equal(hfRoot.get("districtId"), params.districtId()));
            countPredicates.add(cb.equal(countRoot.get("districtId"), params.districtId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        //sorting
        cq.orderBy(cb.desc(hfRoot.get("createdAt")));

        // DTO yaratish
        cq.select(cb
                .construct(
                        HfCustom.class,
                        hfRoot.get("id"),
                        hfRoot.get("name"),
                        hfRoot.get("registryNumber"),
                        hfRoot.get("address"),
                        hfTypeJoin.get("name"),
                        hfRoot.get("legalName"),
                        hfRoot.get("legalTin"),
                        hfRoot.get("registrationDate")
                        ));

        // Qidiruvni amalga oshirish
        TypedQuery<HfCustom> query = em.createQuery(cq);

        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long totalElements = em.createQuery(countQuery).getSingleResult();

        // Pagination uchun sahifani sozlash
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList(), pageable, totalElements);
    }
}
