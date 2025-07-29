package uz.technocorp.ecosystem.modules.inspection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class InspectionRepoImpl implements InspectionRepo {

    private final EntityManager em;
    private final ProfileRepository profileRepository;

    @Override
    public Page<InspectionCustom> getInspectionCustoms(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<InspectionCustom> criteriaQuery = criteriaBuilder.createQuery(InspectionCustom.class);
        Root<Inspection> inspectionRoot = criteriaQuery.from(Inspection.class);
        List<Predicate> predicates = new ArrayList<>();

        Join<Inspection, Profile> profileJoin = inspectionRoot.join("profile", JoinType.LEFT);
        Join<Inspection, Region> regionJoin = inspectionRoot.join("region", JoinType.LEFT);
        Join<Inspection, District> districtJoin = inspectionRoot.join("district", JoinType.LEFT);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Inspection> countRoot = countQuery.from(Inspection.class);
        List<Predicate> countPredicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish

        predicates.add(criteriaBuilder.equal(inspectionRoot.get("intervalId"), intervalId));
        countPredicates.add(criteriaBuilder.equal(countRoot.get("intervalId"), intervalId));

        predicates.add(criteriaBuilder.equal(inspectionRoot.get("status"), status));
        countPredicates.add(criteriaBuilder.equal(countRoot.get("status"), status));

        if (user.getRole().equals(Role.REGIONAL)) {
            if (tin != null) {
                predicates.add(criteriaBuilder.equal(inspectionRoot.get("tin"), tin));
                countPredicates.add(criteriaBuilder.equal(countRoot.get("tin"), tin));
            }
            Profile profile = profileRepository
                    .findById(user.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
            Integer regionId = profile.getRegionId();

            Join<Inspection, Integer> regionsJoin = inspectionRoot.join("regionIds");
            predicates.add(criteriaBuilder.equal(regionsJoin, regionId));

            Join<Inspection, Integer> countRegionsJoin = countRoot.join("regionIds");
            countPredicates.add(criteriaBuilder.equal(countRegionsJoin, regionId));

        } else if (user.getRole().equals(Role.INSPECTOR)) {
            Join<Inspection, UUID> inspectorJoin = inspectionRoot.join("inspectorIds");
            predicates.add(criteriaBuilder.equal(inspectorJoin, user.getId()));

            Join<Inspection, UUID> countInspectorJoin = countRoot.join("inspectorIds");
            countPredicates.add(criteriaBuilder.equal(countInspectorJoin, user.getId()));
        } else if (user.getRole().equals(Role.MANAGER) || user.getRole().equals(Role.HEAD)) {
            if (tin != null) {
                predicates.add(criteriaBuilder.equal(inspectionRoot.get("tin"), tin));
                countPredicates.add(criteriaBuilder.equal(countRoot.get("tin"), tin));
            }
        } else {
            Profile profile = profileRepository
                    .findById(user.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
            Long profileTin = profile.getIdentity();
            predicates.add(criteriaBuilder.equal(inspectionRoot.get("tin"), profileTin));
            countPredicates.add(criteriaBuilder.equal(countRoot.get("tin"), profileTin));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        //sorting
        criteriaQuery.orderBy(criteriaBuilder.asc(inspectionRoot.get("createdAt")));

        criteriaQuery.select(criteriaBuilder
                .construct(
                        InspectionCustom.class,
                        inspectionRoot.get("id"),
                        inspectionRoot.get("tin"),
                        regionJoin.get("name"),
                        districtJoin.get("name"),
                        profileJoin.get("legalName"),
                        profileJoin.get("legalAddress")
                ));

        // Qidiruvni amalga oshirish
        TypedQuery<InspectionCustom> query = em.createQuery(criteriaQuery);

        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long totalElements = em.createQuery(countQuery).getSingleResult();

        // Pagination uchun sahifani sozlash
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList(), pageable, totalElements);
    }
}
