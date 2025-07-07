package uz.technocorp.ecosystem.modules.accreditation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionsView;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
public interface AccreditationRepository extends JpaRepository<Accreditation, UUID> {

    Optional<Accreditation> findByCertificateNumber(String certificateNumber);

    @Query(value = """
            select a.id as id,
            a.tin as legalTin,
            p.legal_address as legalAddress,
            p.legal_name as legalName,
            p.phone_number as phoneNumber,
            p.full_name as fullName,
            a.accreditation_spheres as spheres,
            a.certificate_number as certificateNumber,
            a.certificate_date as certificateDate,
            a.certificate_validity_date as certificateValidityDate
            from accreditation as a
            join profile p on p.tin = a.tin and a.type = :type
            """, nativeQuery = true)
    Page<AccreditationPageView> getAllAccreditations(Pageable pageable, String type);

    @Query(value = """
            select a.id as id,
            a.tin as legalTin,
            p.legal_address as legalAddress,
            p.legal_name as legalName,
            p.phone_number as phoneNumber,
            p.full_name as fullName,
            a.accreditation_spheres as spheres,
            a.certificate_number as certificateNumber,
            a.certificate_date as certificateDate,
            a.certificate_validity_date as certificateValidityDate,
            a.accreditation_commission_decision_path as accreditationCommissionDecisionPath,
            a.assessment_commission_decision_path as assessmentCommissionDecisionPath,
            a.reference_path as referencePath
            from accreditation as a
            join profile p on p.tin = a.tin
            where a.id = :id
            """, nativeQuery = true)
    Optional<AccreditationView> getAccreditation(UUID id);

    @Query(value = """
            select a.id as id,
            a.customer_tin as customerTin,
            a.customer_legal_address as customerLegalAddress,
            p.legal_name as expertOrganizationName,
            a.customer_phone_number as costumerPhoneNumber,
            a.customer_legal_name as customerLegalName,
            a.customer_full_name as customerFullName,
            a.accreditation_spheres as spheres,
            a.expertise_conclusion_number as expertiseConclusionNumber,
            a.expertise_conclusion_date as expertiseConclusionDate,
            a.expertise_object_name as expertiseObjectName
            from accreditation as a
            join profile p on p.tin = a.tin and a.type = :type
            """, nativeQuery = true)
    Page<ExpConclusionPageView> getAllExpertiseConclusions(Pageable pageable, String type);

    @Query(value = """
            select a.id as id,
            a.customer_tin as customerTin,
            a.customer_legal_address as customerLegalAddress,
            p.legal_name as expertOrganizationName,
            a.customer_phone_number as costumerPhoneNumber,
            a.customer_legal_name as customerLegalName,
            a.customer_full_name as customerFullName,
            a.accreditation_spheres as spheres,
            a.expertise_conclusion_number as expertiseConclusionNumber,
            a.expertise_conclusion_date as expertiseConclusionDate,
            a.expertise_object_name as expertiseObjectName,
            a.expertise_conclusion_path as expertiseConclusionPath,
            a.first_symbols_group as firstSymbolsGroup,
            a.second_symbols_group as secondSymbolsGroup,
            a.third_symbols_group as thirdSymbolsGroup,
            a.object_address as objectAddress,
            a.customer_legal_form as customerLegalForm
            from accreditation as a
            join profile p on p.tin = a.tin
            where a.id = :id
            """, nativeQuery = true)
    Optional<ExpConclusionsView> getExpertiseConclusion(UUID id);

    Optional<Accreditation> findByTinAndType(Long tin, AccreditationType type);
}
