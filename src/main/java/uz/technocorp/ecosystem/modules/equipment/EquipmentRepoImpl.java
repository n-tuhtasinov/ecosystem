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
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hftype.HfType;
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

        Pageable pageable = PageRequest.of(params.page() - 1, params.size());

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
        if (params.type() != null){
            ePredicates.add(cb.equal(eRoot.get("type"), params.type()));
            cPredicates.add(cb.equal(cRoot.get("type"), params.type()));
        }
        if (params.legalTin() != null) {
            ePredicates.add(cb.equal(eRoot.get("legalTin"), params.legalTin()));
            cPredicates.add(cb.equal(cRoot.get("legalTin"), params.legalTin()));
        }
        if (params.registryNumber() != null) {
            ePredicates.add(cb.equal(eRoot.get("registryNumber"), params.registryNumber()));
            cPredicates.add(cb.equal(cRoot.get("registryNumber"), params.registryNumber()));
        }
        if (params.startDate() != null) {
            ePredicates.add(cb.greaterThanOrEqualTo(eRoot.get("registrationDate"), params.startDate()));
            cPredicates.add(cb.greaterThanOrEqualTo(cRoot.get("registrationDate"), params.startDate()));
        }
        if (params.endDate() != null) {
            ePredicates.add(cb.lessThanOrEqualTo(eRoot.get("registrationDate"), params.endDate()));
            cPredicates.add(cb.lessThanOrEqualTo(cRoot.get("registrationDate"), params.endDate()));
        }
        if (params.regionId() != null) {
            ePredicates.add(cb.equal(eRoot.get("regionId"), params.regionId()));
            cPredicates.add(cb.equal(cRoot.get("regionId"), params.regionId()));
        }
        if (params.districtId() != null) {
            ePredicates.add(cb.equal(eRoot.get("districtId"), params.districtId()));
            cPredicates.add(cb.equal(cRoot.get("districtId"), params.districtId()));
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
}
