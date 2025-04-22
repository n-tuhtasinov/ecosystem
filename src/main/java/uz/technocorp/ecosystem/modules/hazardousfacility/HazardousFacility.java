package uz.technocorp.ecosystem.modules.hazardousfacility;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hazardousfacility.enums.HFSphere;
import uz.technocorp.ecosystem.modules.hazardousfacilitytype.HazardousFacilityType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class HazardousFacility extends AuditEntity {

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private Long serialNumber;

    @Column(nullable = false, unique = true)
    private String registryNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

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

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, columnDefinition = "text")
    private String hazardousSubstance;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "registration_appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "registration_appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacilityType.class)
    @JoinColumn(name = "hazardous_facility_type_id", insertable = false, updatable = false)
    private HazardousFacilityType hazardousFacilityType;

    @Column(name = "hazardous_facility_type_id")
    private Integer hazardousFacilityTypeId;

    private String extraArea;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    private List<HFSphere> spheres;

    @Column(columnDefinition = "text")
    private String deregistrationReason;

    private String deregistrationFilePath;

    @Column(columnDefinition = "text")
    private String periodicUpdateReason;

    private String periodicUpdateFilePath;

    private boolean active;

    //Identifikatsiya varag'i
    private String identificationCardPath;

    //XICHOni ro'yxatga olish uchun to'lov kvitansiyasi
    private String receiptPath;

    //Ekspertiza xulosasi
    private String expertOpinionPath;

    //Loyiha hujjatlari
    private String projectDocumentationPath;

    //XICHO kadastr pasporti
    private String cadastralPassportPath;

    //Sanoat xavfsizligi deklaratsiyasi
    private String industrialSafetyDeclarationPath;

    //Sug'urta polisi
    private String insurancePolicyPath;

    //Litsenziya
    private String licensePath;

    //Ruxsatnoma
    private String permitPath;

    //XICHO xodimlarining sanoat xavfsizligi bo'yicha attestatsiyadan o'tganligi
    private String certificationPath;

    //    //Yong'in xavfsizligi xulosasi
    //    private String fireSafetyReportPath;

    //Qurilmalarni sinovdan o'tganligi
    private String deviceTestingPath;

    //Mas'ul xodim tayinlanganligi buyrug'i
    private String appointmentOrderPath;

    //Ekologiya qo'mitasi xulosasi -> Qurilmalar ekspertizasi
    private String ecologicalConclusionPath;

    //Arizaga javob xati
    private String replyLetterPath;

//    public HazardousFacility(Long legalTin, String legalName, Integer regionId, Integer districtId,
//                             UUID profileId, String legalAddress, String phoneNumber, String email,
//                             String upperOrganization, String name, String address, UUID appealId,
//                             Integer hazardousFacilityTypeId, String extraArea, String description,
//                             String registryNumber) {
//        this.legalTin = legalTin;
//        this.legalName = legalName;
//        this.regionId = regionId;
//        this.districtId = districtId;
//        this.profileId = profileId;
//        this.legalAddress = legalAddress;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.upperOrganization = upperOrganization;
//        this.name = name;
//        this.address = address;
//        this.appealId = appealId;
//        this.hazardousFacilityTypeId = hazardousFacilityTypeId;
//        this.extraArea = extraArea;
//        this.description = description;
//        this.registryNumber = registryNumber;
//        this.active = true;
//    }
}
