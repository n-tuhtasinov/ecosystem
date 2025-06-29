package uz.technocorp.ecosystem.modules.declaration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.cadastrepassport.CadastrePassport;
import uz.technocorp.ecosystem.modules.declaration.dto.DeclarationParams;
import uz.technocorp.ecosystem.modules.declaration.view.DeclarationView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
@Repository
@RequiredArgsConstructor
public class DeclarationRepoImpl implements DeclarationRepo {

    private final EntityManager entityManager;

    @Override
    public Page<DeclarationView> getAllByParams(DeclarationParams params) {

        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getSize());

        // select uchun alohida query va root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeclarationView> cadQuery = cb.createQuery(DeclarationView.class);
        Root<Declaration> iRoot = cadQuery.from(Declaration.class);
        List<Predicate> iPredicates = new ArrayList<>();

        // count uchun alohida query va root
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CadastrePassport> cRoot = countQuery.from(CadastrePassport.class);
        List<Predicate> cPredicates = new ArrayList<>();


        // Dinamik qidiruv shartlarini qo'shish
        if (params.getLegalTin() != null) {
            iPredicates.add(cb.equal(iRoot.get("legalTin"), params.getLegalTin()));
            cPredicates.add(cb.equal(cRoot.get("legalTin"), params.getLegalTin()));
        }
        if (params.getSearch() != null && !params.getSearch().isBlank()) {
            iPredicates.add(cb.equal(iRoot.get("registryNumber"), params.getSearch()));
            cPredicates.add(cb.equal(cRoot.get("registryNumber"), params.getSearch()));
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
        cadQuery.select(cb
                .construct(
                        DeclarationView.class,
                        iRoot.get("id"),
                        iRoot.get("registryNumber"),
                        iRoot.get("createdAt"),
                        iRoot.get("hfRegistryNumber"),
                        iRoot.get("hfName"),
                        iRoot.get("hfAddress"),
                        iRoot.get("producingOrganizationName"),
                        iRoot.get("producingOrganizationTin")
                ));
        cadQuery.where(iPredicates.toArray(new Predicate[0]));
        cadQuery.orderBy(cb.desc(iRoot.get("createdAt")));
        TypedQuery<DeclarationView> eQuery = entityManager.createQuery(cadQuery);

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
