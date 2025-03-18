package uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.projection.HazardousFacilityRegistrationAppealView;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityRegistrationAppealRepository extends JpaRepository<HazardousFacilityRegistrationAppeal, UUID> {

    @Query(value = """
            select cast(h.id as varchar)                             as id,
                   cast(h.appeal_id as varchar)                      as appeal_id,
                   appeal_type                                       as appealType,
                   h.number                                          as number,
                   order_number                                      as orderNumber,
                   legal_tin                                         as legalTin,
                   legal_name                                        as legalName,
                   legal_address                                     as legalAddress,
                   phone_number                                      as phoneNumber,
                   upper_organization                                as upperOrganization,
                   h.name as name,
                   address,
                   extra_area                                          as extraArea,
                   email,
                   h.description                                       as description,
                   r.name                                              as regionName,
                   d.name                                              as districtName,
                   r.id                                                as regionId,
                   d.id                                                as districtId,
                   hazardous_facility_type_id                          as hazardousFacilityTypeId,
                   ht.name                                             as hazardousFacilityTypeName,
                   cast(identification_card_path as varchar)           as identificationCardPath,
                   cast(receipt_path as varchar)                       as receiptPath,
                   cast(license_path as varchar)                       as licensePath,
                   cast(appointment_order_path as varchar)             as appointmentOrderPath,
                   cast(cadastral_passport_path as varchar)            as cadastralPassportPath,
                   cast(certification_path as varchar)                 as certificationPath,
                   cast(device_testing_path as varchar)                as deviceTestingPath,
                   cast(ecological_conclusion_path as varchar)         as ecologicalConclusionPath,
                   cast(expert_opinion_path as varchar)                as expertOpinionPath,
                   cast(industrial_safety_declaration_path as varchar) as industrialSafetyDeclarationPath,
                   cast(insurance_policy_path as varchar)              as insurancePolicyPath,
                   cast(permit_path as varchar)                        as permitPath,
                   cast(project_documentation_path as varchar)         as projectDocumentationPath
            from hazardous_facility_registration_appeal h
                     join region r on h.region_id = r.id
                     join district d on h.district_id = d.id
                     join hazardous_facility_type ht on h.hazardous_facility_type_id = ht.id
            where h.id = :id
            """, nativeQuery = true)
    Optional<HazardousFacilityRegistrationAppealView> getFullInfoById(UUID id);

    @Query("SELECT h.orderNumber FROM HazardousFacilityRegistrationAppeal h ORDER BY h.orderNumber DESC LIMIT 1")
    Integer findMaxOrderNumber();
}
