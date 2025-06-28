package uz.technocorp.ecosystem.modules.equipment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class EquipmentRepoImpl implements EquipmentRepo{

    private final EntityManager entityManager;

    @Override
    public Page<EquipmentView> getAllByParams(User user, EquipmentParams params) {

        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getSize());

        // select uchun alohida query va root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EquipmentView> equipmentQuery = cb.createQuery(EquipmentView.class);
        Root<Equipment> eRoot = equipmentQuery.from(Equipment.class);
        List<Predicate> ePredicates = new ArrayList<>();

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Equipment> cRoot = countQuery.from(Equipment.class);
        List<Predicate> cPredicates = new ArrayList<>();

        // JOIN — hazardousFacility bilan LEFT JOIN qilish
        Join<Equipment, HazardousFacility> hfJoin = eRoot.join("hazardousFacility", JoinType.LEFT);

        // Dinamik qidiruv shartlarini qo'shish
        if (params.getType() != null){
            ePredicates.add(cb.equal(eRoot.get("type"), params.getType()));
            cPredicates.add(cb.equal(cRoot.get("type"), params.getType()));
        }
        if (params.getLegalTin() != null) {
            ePredicates.add(cb.equal(eRoot.get("legalTin"), params.getLegalTin()));
            cPredicates.add(cb.equal(cRoot.get("legalTin"), params.getLegalTin()));
        }
        if (params.getRegistryNumber() != null) {
            ePredicates.add(cb.equal(eRoot.get("registryNumber"), params.getRegistryNumber()));
            cPredicates.add(cb.equal(cRoot.get("registryNumber"), params.getRegistryNumber()));
        }
        if (params.getStartDate() != null) {
            ePredicates.add(cb.greaterThanOrEqualTo(eRoot.get("registrationDate"), params.getStartDate()));
            cPredicates.add(cb.greaterThanOrEqualTo(cRoot.get("registrationDate"), params.getStartDate()));
        }
        if (params.getEndDate() != null) {
            ePredicates.add(cb.lessThanOrEqualTo(eRoot.get("registrationDate"), params.getEndDate()));
            cPredicates.add(cb.lessThanOrEqualTo(cRoot.get("registrationDate"), params.getEndDate()));
        }
        if (params.getRegionId() != null) {
            ePredicates.add(cb.equal(eRoot.get("regionId"), params.getRegionId()));
            cPredicates.add(cb.equal(cRoot.get("regionId"), params.getRegionId()));
        }
        if (params.getDistrictId() != null) {
            ePredicates.add(cb.equal(eRoot.get("districtId"), params.getDistrictId()));
            cPredicates.add(cb.equal(cRoot.get("districtId"), params.getDistrictId()));
        }

        // DTO yaratish -> condition -> sorting -> run
        equipmentQuery.select(cb
                .construct(
                        EquipmentView.class,
                        eRoot.get("id"),
                        eRoot.get("registrationDate"),
                        eRoot.get("registryNumber"),
                        eRoot.get("type"),
                        eRoot.get("legalName"),
                        eRoot.get("legalTin"),
                        eRoot.get("legalAddress"),
                        hfJoin.get("name"),
                        eRoot.get("address"),
                        eRoot.get("factoryNumber")
                ));
        equipmentQuery.where(ePredicates.toArray(new Predicate[0]));
        equipmentQuery.orderBy(cb.desc(eRoot.get("createdAt")));
        TypedQuery<EquipmentView> eQuery = entityManager.createQuery(equipmentQuery);

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

    @Override
    public Long countByParams(Long legalTin, Integer regionId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Equipment> countRoot = countQuery.from(Equipment.class);
        List<Predicate> countPredicates = new ArrayList<>();

        if (legalTin != null) {
            countPredicates.add(cb.equal(countRoot.get("legalTin"), legalTin));
        }
        if (regionId != null) {
            countPredicates.add(cb.equal(countRoot.get("regionId"), regionId));
        }

        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
