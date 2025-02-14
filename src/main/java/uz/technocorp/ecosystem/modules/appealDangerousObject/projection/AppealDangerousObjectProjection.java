package uz.technocorp.ecosystem.modules.appealDangerousObject.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
public interface AppealDangerousObjectProjection {

    UUID getId();

    Integer getAppealTypeId();

    String getNumber();

    String getOrderNumber();

    Long getLegal_tin();

    String getLegalName();

    Integer getRegionId();

    String getRegionName();

    Integer getDistrictId();

    String getDistrictName();

    String getLegalAddress();

    String getPhoneNumber();

    String getEmail();

    String getUpperOrganization();

    String getName();

    String getAddress();

    Integer getDangerousObjectTypeId();

    String getDangerousObjectName();

    String getExtraArea();

    String getDescription();

    String getObjectNumber();

    UUID getIdentificationCardId();

    UUID getReceiptId();

    UUID getLicenseId();

    UUID getAppointmentOrderId();

    UUID getCadastralPassportId();

    UUID getCertificationId();

    UUID getDeviceTestingId();

    UUID getEcologicalConclusionId();

    UUID getExpertOpinionId();

    UUID getFireSafetyReportId();

    UUID getIndustrialSafetyDeclarationId();

    UUID getInsurancePolicyId();

    UUID getPermitId();

    UUID getProjectDocumentationId();
}
