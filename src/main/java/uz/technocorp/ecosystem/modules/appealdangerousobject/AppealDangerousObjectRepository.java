package uz.technocorp.ecosystem.modules.appealdangerousobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.appealdangerousobject.projection.AppealDangerousObjectProjection;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealDangerousObjectRepository extends JpaRepository<AppealDangerousObject, UUID> {

    @Query(value = """
            select cast(ado.id as varchar)                           as id,
                   cast(ado.appeal_id as varchar)                    as appeal_id,
                   appeal_type_id                                    as appealTypeId,
                   number,
                   order_number                                      as orderNumber,
                   legal_tin                                         as legalTin,
                   legal_name                                        as legalName,
                   legal_address                                     as legalAddress,
                   phone_number                                      as phoneNumber,
                   upper_organization                                as upperOrganization,
                   ado.name as name,
                   address,
                   extra_area                                        as extraArea,
                   email,
                   description,
                   object_number                                     as objectNumber,
                   r.name                                            as regionName,
                   d.name                                            as districtName,
                   r.id                                              as regionId,
                   d.id                                              as districtId,
                   a.name                                               appealTypeName,
                   dangerous_object_type_id                          as dangerousObjectTypeId,
                   dot.name                                          as dangerousObjectTypeName,
                   cast(identification_card_id as varchar)           as identificationCardId,
                   cast(receipt_id as varchar)                       as receiptId,
                   cast(license_id as varchar)                       as licenseId,
                   cast(appointment_order_id as varchar)             as appointmentOrderId,
                   cast(cadastral_passport_id as varchar)            as cadastralPassportId,
                   cast(certification_id as varchar)                 as certificationId,
                   cast(device_testing_id as varchar)                as deviceTestingId,
                   cast(ecological_conclusion_id as varchar)         as ecologicalConclusionId,
                   cast(expert_opinion_id as varchar)                as expertOpinionId,
                   cast(fire_safety_report_id as varchar)            as fireSafetyReportId,
                   cast(industrial_safety_declaration_id as varchar) as industrialSafetyDeclarationId,
                   cast(insurance_policy_id as varchar)              as insurancePolicyId,
                   cast(permit_id as varchar)                        as permitId,
                   cast(project_documentation_id as varchar)         as projectDocumentationId
            from appeal_dangerous_object ado
                     join appeal_type a on a.id = ado.appeal_type_id
                     join region r on ado.region_id = r.id
                     join district d on ado.district_id = d.id
                     join dangerous_object_type dot on ado.dangerous_object_type_id = dot.id
            where ado.id = :id
            """, nativeQuery = true)
    Optional<AppealDangerousObjectProjection> getFullInfoById(UUID id);
}
