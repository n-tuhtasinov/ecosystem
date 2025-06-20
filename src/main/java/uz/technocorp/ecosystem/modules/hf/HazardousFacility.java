package uz.technocorp.ecosystem.modules.hf;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;
import uz.technocorp.ecosystem.modules.hftype.HfType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
public class HazardousFacility extends BaseEntity {

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private Long orderNumber;

    private LocalDate registrationDate;

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

    @Column(nullable = false)
    private String legalAddress;

    private String phoneNumber;

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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HfType.class)
    @JoinColumn(name = "hf_type_id", insertable = false, updatable = false)
    private HfType hfType;

    @Column(name = "hf_type_id")
    private Integer hfTypeId;

    private String extraArea;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    private List<HFSphere> spheres;

    @Column(columnDefinition = "text")
    private String deregisterReason;

    private String deregisterFilePath;

    @Column(columnDefinition = "text")
    private String periodicUpdateReason;

    private String periodicUpdateFilePath;

    private boolean active;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> files;

//    //Identifikatsiya varag'i
//    private String identificationCardPath;
//
//    //XICHOni ro'yxatga olish uchun to'lov kvitansiyasi
//    private String receiptPath;
//
//    //Ekspertiza xulosasi
//    private String expertOpinionPath;
//
//    //Loyiha hujjatlari
//    private String projectDocumentationPath;
//
//    //XICHO kadastr pasporti
//    private String cadastralPassportPath;
//
//    //Sanoat xavfsizligi deklaratsiyasi
//    private String industrialSafetyDeclarationPath;
//
//    //Sug'urta polisi
//    private String insurancePolicyPath;
//
//    //Litsenziya
//    private String licensePath;
//
//    //Ruxsatnoma
//    private String permitPath;
//
//    //XICHO xodimlarining sanoat xavfsizligi bo'yicha attestatsiyadan o'tganligi
//    private String certificationPath;
//
//    //Qurilmalarni sinovdan o'tganligi
//    private String deviceTestingPath;
//
//    //Mas'ul xodim tayinlanganligi buyrug'i
//    private String appointmentOrderPath;
//
//    //Ekologiya qo'mitasi xulosasi -> Qurilmalar ekspertizasi
//    private String ecologicalConclusionPath;

    // Reestr fayl path
    private String registryFilePath;

    @Column(nullable = false)
    private String inspectorName;


}
