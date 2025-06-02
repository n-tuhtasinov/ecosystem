package uz.technocorp.ecosystem.modules.hf;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
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
    public Page<HfCustom> getHfCustoms(User user, int page, int size, Long legalTin, String registryNumber, Integer regionId, LocalDate startDate, LocalDate endDate) {

        Pageable pageable= PageRequest.of(page-1, size);

        // select uchun alohida query va root
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HfCustom> cq = cb.createQuery(HfCustom.class);
        Root<HazardousFacility> hfRoot = cq.from(HazardousFacility.class);
        List<Predicate> predicates = new ArrayList<>();

        // JOIN — Region bilan LEFT JOIN qilish
        Join<HazardousFacility, Region> regionJoin = hfRoot.join("region", JoinType.LEFT);

        // JOIN — District bilan LEFT JOIN qilish
        Join<HazardousFacility, District> districtJoin = hfRoot.join("district", JoinType.LEFT);

        // JOIN — Region bilan LEFT JOIN qilish
        Join<HazardousFacility, HfType> hfTypeJoin = hfRoot.join("hfType", JoinType.LEFT);

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Appeal> countRoot = countQuery.from(Appeal.class);
        List<Predicate> countPredicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish
        if (legalTin != null) {
            predicates.add(cb.equal(hfRoot.get("legalTin"), legalTin));
            countPredicates.add(cb.equal(countRoot.get("legalTin"), legalTin));
        }
        if (registryNumber != null) {
            predicates.add(cb.equal(hfRoot.get("registryNumber"), registryNumber));
            countPredicates.add(cb.equal(countRoot.get("registryNumber"), registryNumber));
        }
        if (startDate != null) {
            predicates.add(cb.between(hfRoot.get("registrationDate"), startDate, endDate));
            countPredicates.add(cb.between(countRoot.get("registrationDate"), startDate, endDate));
        }

        if (regionId != null) {
            predicates.add(cb.equal(hfRoot.get("regionId"), regionId));
            countPredicates.add(cb.equal(countRoot.get("regionId"), regionId));
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
                        regionJoin.get("name"),
                        districtJoin.get("name"),
                        hfRoot.get("address"),
                        hfTypeJoin.get("name"),
                        hfRoot.get("email"),
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
