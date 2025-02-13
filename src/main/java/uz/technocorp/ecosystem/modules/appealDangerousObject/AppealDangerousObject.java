package uz.technocorp.ecosystem.modules.appealDangerousObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appealType.AppealType;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.dangerousObjectType.DangerousObjectType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppealDangerousObject extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppealType.class)
    @JoinColumn(name = "appeal_type_id", insertable = false, updatable = false)
    private AppealType appealType;

    @Column(name = "appeal_type_id")
    private Integer appealTypeId;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private Long legal_tin;

    @Column(nullable = false)
    private String legalName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    private String regionName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    private String districtName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    private String legalAddress;

    private String phoneNumber;

    private String email;

    private String upperOrganization;

    private String name;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DangerousObjectType.class)
    @JoinColumn(name = "dangerous_object_type_id", insertable = false, updatable = false)
    private DangerousObjectType dangerousObjectType;

    @Column(name = "dangerous_object_type_id")
    private Integer dangerousObjectTypeId;

    private String extraArea;

    private String description;

    private String objectNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "identification_card_id", insertable = false, updatable = false)
    private Attachment identificationCard;

    @Column(name = "identification_card_id")
    private UUID identificationCardId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "receipt_id", insertable = false, updatable = false)
    private Attachment receipt;

    @Column(name = "receipt_id")
    private UUID receiptId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "expert_opinion_id", insertable = false, updatable = false)
    private Attachment expertOpinion;

    @Column(name = "expert_opinion_id")
    private UUID expertOpinionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "project_documentation_id", insertable = false, updatable = false)
    private Attachment projectDocumentation;

    @Column(name = "project_documentation_id")
    private UUID projectDocumentationId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "cadastral_passport_id", insertable = false, updatable = false)
    private Attachment cadastralPassport;

    @Column(name = "cadastral_passport_id")
    private UUID cadastralPassportId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "industrial_safety_declaration_id", insertable = false, updatable = false)
    private Attachment industrialSafetyDeclaration;

    @Column(name = "industrial_safety_declaration_id")
    private UUID industrialSafetyDeclarationId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "insurance_policy_id", insertable = false, updatable = false)
    private Attachment insurancePolicy;

    @Column(name = "insurance_policy_id")
    private UUID incidentPassportId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "license_id", insertable = false, updatable = false)
    private Attachment license;

    @Column(name = "license_id")
    private UUID licenseId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "permit_id", insertable = false, updatable = false)
    private Attachment permit;

    @Column(name = "permit_id")
    private UUID permitId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "certification_id", insertable = false, updatable = false)
    private Attachment certification;

    @Column(name = "certification_id")
    private UUID certificationId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "fire_safety_report_id", insertable = false, updatable = false)
    private Attachment fireSafetyReport;

    @Column(name = "fire_safety_report_id")
    private UUID fireSafetyReportId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "device_testing_id", insertable = false, updatable = false)
    private Attachment deviceTesting;

    @Column(name = "device_testing_id")
    private UUID deviceTestingId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "appointment_order_id", insertable = false, updatable = false)
    private Attachment appointmentOrder;

    @Column(name = "appointment_order_id")
    private UUID appointmentOrderId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "ecological_conclusion_id", insertable = false, updatable = false)
    private Attachment ecologicalConclusion;

    @Column(name = "ecological_conclusion_id")
    private UUID ecologicalConclusionId;

    public AppealDangerousObject(Integer appealTypeId, String number, String orderNumber, Long legal_tin, String legalName, Integer regionId, String regionName, Integer districtId, String districtName, UUID profileId, String legalAddress, String phoneNumber, String email, String upperOrganization, String name, String address, Integer dangerousObjectTypeId, String extraArea, String description, String objectNumber, UUID identificationCardId, UUID receiptId) {
        this.appealTypeId = appealTypeId;
        this.number = number;
        this.orderNumber = orderNumber;
        this.legal_tin = legal_tin;
        this.legalName = legalName;
        this.regionId = regionId;
        this.regionName = regionName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.profileId = profileId;
        this.legalAddress = legalAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.upperOrganization = upperOrganization;
        this.name = name;
        this.address = address;
        this.dangerousObjectTypeId = dangerousObjectTypeId;
        this.extraArea = extraArea;
        this.description = description;
        this.objectNumber = objectNumber;
        this.identificationCardId = identificationCardId;
        this.receiptId = receiptId;
    }
}
