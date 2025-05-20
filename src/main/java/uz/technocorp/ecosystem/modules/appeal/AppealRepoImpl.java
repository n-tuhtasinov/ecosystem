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
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
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
    private final ProfileRepository profileRepository;
    private final OfficeRepository officeRepository;

    @Override
    public Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "created_at");

        // select uchun alohida query va root
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AppealCustom> cq = cb.createQuery(AppealCustom.class);
        Root<Appeal> appealRoot = cq.from(Appeal.class);
        List<Predicate> predicates = new ArrayList<>();

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Appeal> countRoot = countQuery.from(Appeal.class);
        List<Predicate> countPredicates = new ArrayList<>();

        // Dinamik qidiruv shartlarini qo'shish
        if (params.get("status")!= null && !params.get("status").isEmpty()) {
            predicates.add(cb.equal(appealRoot.get("status"), AppealStatus.valueOf(params.get("status"))));
            countPredicates.add(cb.equal(countRoot.get("status"), AppealStatus.valueOf(params.get("status"))));
        }
        if (params.get("appealType") != null && !params.get("appealType").isEmpty()) {
            predicates.add(cb.equal(appealRoot.get("appealType"), AppealType.valueOf(params.get("params"))));
            countPredicates.add(cb.equal(countRoot.get("appealType"), AppealType.valueOf(params.get("params"))));
        }
        if (params.get("legalTin")!= null && !params.get("legalTin").isEmpty()) {
            predicates.add(cb.equal(appealRoot.get("legalTin"), params.get("legalTin")));
            countPredicates.add(cb.equal(countRoot.get("legalTin"), params.get("legalTin")));
        }

        if (params.get("startDate") != null && !params.get("startDate").isEmpty()) {
//            predicates.add(cb.equal(appeal.get("date"), params.get("date")));
            predicates.add(cb.between(appealRoot.get("createdAt"), LocalDate.parse(params.get("startDate")).atStartOfDay(), LocalDate.parse(params.get("endDate")).atTime(23,59,59)));
            countPredicates.add(cb.between(countRoot.get("createdAt"), LocalDate.parse(params.get("startDate")).atStartOfDay(), LocalDate.parse(params.get("endDate")).atTime(23,59,59)));
        }

        if (params.get("officeId") != null) {
            predicates.add(cb.equal(appealRoot.get("officeId"), params.get("officeId")));
            countPredicates.add(cb.equal(countRoot.get("officeId"), params.get("officeId")));
        }

        if (params.get("executorId") != null) {
            predicates.add(cb.equal(appealRoot.get("executorId"), params.get("executorId")));
            countPredicates.add(cb.equal(countRoot.get("executorId"), params.get("executorId")));
        }

        //get profile by user
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", user.getProfileId()));


        //to display data by user role
        if (user.getRole().equals(Role.LEGAL)){
            predicates.add(cb.equal(appealRoot.get("profileId"), user.getProfileId()));
            countPredicates.add(cb.equal(countRoot.get("profileId"), user.getProfileId()));
        } else if (user.getRole().equals(Role.INSPECTOR)) {
            predicates.add(cb.equal(appealRoot.get("executorId"), user.getId()));
            countPredicates.add(cb.equal(countRoot.get("executorId"), user.getId()));
        } else if (user.getRole().equals(Role.REGIONAL)) {
            //TODO: Regional roli uchun ko'rinishni qilish kerak
        }else {
            //TODO: Qolgan roli uchun ko'rinishni qilish kerak
        }

        cq.where(predicates.toArray(new Predicate[0]));

        //sorting
        cq.orderBy(cb.desc(appealRoot.get("createdAt")));

        // DTO yaratish
        cq.select(cb
                .construct(
                        AppealCustom.class,
                        appealRoot.get("id"),
                        appealRoot.get("createdAt"),
                        appealRoot.get("status"),
                        appealRoot.get("legalTin"),
                        appealRoot.get("number"),
                        appealRoot.get("legalName"),
                        appealRoot.get("regionName"),
                        appealRoot.get("districtName"),
                        appealRoot.get("address"),
                        appealRoot.get("phoneNumber"),
                        appealRoot.get("appealType"),
                        appealRoot.get("executorName"),
                        appealRoot.get("deadline"),
                        appealRoot.get("officeName")
                ));

        // Qidiruvni amalga oshirish
        TypedQuery<AppealCustom> query = em.createQuery(cq);

        // Pagination uchun sahifani sozlash
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long totalElements = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, totalElements);
    }
}
