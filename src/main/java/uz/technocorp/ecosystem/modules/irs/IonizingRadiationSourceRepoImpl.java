package uz.technocorp.ecosystem.modules.irs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.irs.dto.IrsParams;
import uz.technocorp.ecosystem.modules.irs.view.IrsView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 09.06.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class IonizingRadiationSourceRepoImpl implements IonizingRadiationSourceRepo {

    private final EntityManager entityManager;

    @Override
    public Page<IrsView> getAll(IrsParams params) {

        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getSize());

        // select uchun alohida query va root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<IrsView> irsQuery = cb.createQuery(IrsView.class);
        Root<IonizingRadiationSource> iRoot = irsQuery.from(IonizingRadiationSource.class);
        List<Predicate> iPredicates = new ArrayList<>();

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<IonizingRadiationSource> cRoot = countQuery.from(IonizingRadiationSource.class);
        List<Predicate> cPredicates = new ArrayList<>();

        // JOIN — hazardousFacility bilan LEFT JOIN qilish
//        Join<Equipment, HazardousFacility> hfJoin = iRoot.join("hazardousFacility", JoinType.LEFT);

        // Dinamik qidiruv shartlarini qo'shish
        if (params.getLegalTin() != null) {
            iPredicates.add(cb.equal(iRoot.get("legalTin"), params.getLegalTin()));
            cPredicates.add(cb.equal(cRoot.get("legalTin"), params.getLegalTin()));
        }
        if (params.getRegistryNumber() != null) {
            iPredicates.add(cb.equal(iRoot.get("registryNumber"), params.getRegistryNumber()));
            cPredicates.add(cb.equal(cRoot.get("registryNumber"), params.getRegistryNumber()));
        }
        if (params.getStartDate() != null) {
            iPredicates.add(cb.greaterThanOrEqualTo(iRoot.get("registrationDate"), params.getStartDate()));
            cPredicates.add(cb.greaterThanOrEqualTo(cRoot.get("registrationDate"), params.getStartDate()));
        }
        if (params.getEndDate() != null) {
            iPredicates.add(cb.lessThanOrEqualTo(iRoot.get("registrationDate"), params.getEndDate()));
            cPredicates.add(cb.lessThanOrEqualTo(cRoot.get("registrationDate"), params.getEndDate()));
        }
        if (params.getRegionId() != null) {
            iPredicates.add(cb.equal(iRoot.get("regionId"), params.getRegionId()));
            cPredicates.add(cb.equal(cRoot.get("regionId"), params.getRegionId()));
        }
        if (params.getDistrictId() != null) {
            iPredicates.add(cb.equal(iRoot.get("districtId"), params.getDistrictId()));
            cPredicates.add(cb.equal(cRoot.get("districtId"), params.getDistrictId()));
        }

        // DTO yaratish -> condition -> sorting -> run
        irsQuery.select(cb
                .construct(
                        IrsView.class,
                        iRoot.get("id"),
                        iRoot.get("registrationDate"),
                        iRoot.get("registryNumber"),
                        iRoot.get("address"),
                        iRoot.get("symbol"),
                        iRoot.get("sphere"),
                        iRoot.get("factoryNumber"),
                        iRoot.get("activity"),
                        iRoot.get("category"),
                        iRoot.get("isValid"),
                        iRoot.get("usageType")
                ));
        irsQuery.where(iPredicates.toArray(new Predicate[0]));
        irsQuery.orderBy(cb.desc(iRoot.get("createdAt")));
        TypedQuery<IrsView> eQuery = entityManager.createQuery(irsQuery);

        // countni olish uchun - select -> condition -> run -> get count
        countQuery.select(cb.count(cRoot));
        countQuery.where(cPredicates.toArray(new Predicate[0]));
        TypedQuery<Long> cQuery = entityManager.createQuery(countQuery);
        Long totalElements = cQuery.getSingleResult();

        //Pagination
        eQuery.setFirstResult((int) pageable.getOffset());
        eQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(eQuery.getResultList(), pageable, totalElements);
    }
}
