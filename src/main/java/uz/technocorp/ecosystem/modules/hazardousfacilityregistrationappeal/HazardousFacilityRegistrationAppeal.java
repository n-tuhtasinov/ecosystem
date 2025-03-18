package uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.hazardousfacilitytype.HazardousFacilityType;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class HazardousFacilityRegistrationAppeal extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

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

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacilityType.class)
    @JoinColumn(name = "hazardous_facility_type_id", insertable = false, updatable = false)
    private HazardousFacilityType hazardousFacilityType;

    @Column(name = "hazardous_facility_type_id")
    private Integer hazardousFacilityTypeId;

    private String extraArea;

    @Column(columnDefinition = "text")
    private String description;

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

}
