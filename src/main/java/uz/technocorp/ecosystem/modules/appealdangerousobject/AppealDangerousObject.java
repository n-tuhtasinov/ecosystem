package uz.technocorp.ecosystem.modules.appealdangerousobject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.dangerousobjecttype.DangerousObjectType;
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

    @Enumerated(EnumType.STRING)
    private AppealType appealType;

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

    public AppealDangerousObject(AppealType appealType, String number, String orderNumber, Long legal_tin, String legalName, Integer regionId, String regionName, Integer districtId, String districtName, UUID profileId, String legalAddress, String phoneNumber, String email, String upperOrganization, String name, String address, Integer dangerousObjectTypeId, String extraArea, String description, String objectNumber, String identificationCardPath, String receiptPath) {
        this.appealType = appealType;
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
        this.identificationCardPath = identificationCardPath;
        this.receiptPath = receiptPath;
    }
}
