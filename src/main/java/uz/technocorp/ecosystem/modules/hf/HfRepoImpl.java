package uz.technocorp.ecosystem.modules.hf;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hftype.HfType;

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
    public Page<HfCustom> getHfCustoms(HfParams params) {

        Pageable pageable= PageRequest.of(params.getPage()-1, params.getSize());

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
        if (params.getLegalTin() != null) {
            predicates.add(cb.equal(hfRoot.get("legalTin"), params.getLegalTin()));
            countPredicates.add(cb.equal(countRoot.get("legalTin"), params.getLegalTin()));
        }
        if (params.getRegistryNumber() != null) {
            predicates.add(cb.equal(hfRoot.get("registryNumber"), params.getRegistryNumber()));
            countPredicates.add(cb.equal(countRoot.get("registryNumber"), params.getRegistryNumber()));
        }
        if (params.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(hfRoot.get("registrationDate"), params.getStartDate()));
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("registrationDate"), params.getStartDate()));
        }

        if (params.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(hfRoot.get("registrationDate"), params.getEndDate()));
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("registrationDate"), params.getEndDate()));
        }

        if (params.getRegionId() != null) {
            predicates.add(cb.equal(hfRoot.get("regionId"), params.getRegionId()));
            countPredicates.add(cb.equal(countRoot.get("regionId"), params.getRegionId()));
        }

        if (params.getDistrictId() != null) {
            predicates.add(cb.equal(hfRoot.get("districtId"), params.getDistrictId()));
            countPredicates.add(cb.equal(countRoot.get("districtId"), params.getDistrictId()));
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
                        hfRoot.get("legalAddress"),
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

    @Override
    public Long countByParams(Long legalTin, Integer regionId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<HazardousFacility> countRoot = countQuery.from(HazardousFacility.class);
        List<Predicate> countPredicates = new ArrayList<>();

        if (legalTin != null) {
            countPredicates.add(cb.equal(countRoot.get("legalTin"), legalTin));
        }
        if (regionId != null) {
            countPredicates.add(cb.equal(countRoot.get("regionId"), regionId));
        }

        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        return em.createQuery(countQuery).getSingleResult();
    }
}
