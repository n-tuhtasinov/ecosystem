package uz.technocorp.ecosystem.modules.equipment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSort;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.03.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Equipment extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @Column(nullable = false, unique = true)
    private String registryNumber;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hazardous_facility_id", insertable = false, updatable = false)
    private HazardousFacility hazardousFacility;

    @Column(name = "hazardous_facility_id")
    private UUID hazardousFacilityId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipment.class, optional = false)
    @JoinColumn(name = "child_equipment_id", insertable = false, updatable = false)
    private ChildEquipment childEquipment;

    @Column(name = "child_equipment_id", nullable = false)
    private Integer childEquipmentId;

    @Column(nullable = false)
    private String factoryNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class, optional = false)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id", nullable = false)
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class, optional = false)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(nullable = false)
    private String address;

    @Column
    private String model;

    @Column
    private String factory;

    @Column
    private String location;

    @Column(nullable = false)
    private LocalDate manufacturedAt;

    private LocalDate partialCheckDate; // qisman texnik ko'rik / tashqi va ichki ko'rik

    private LocalDate fullCheckDate;  // to'liq texnik ko'rik / gidrosinov/ keyimgi tekshirish (100ming)

    //previous registration number.
    //it is used when the device is re-registered
//    private String oldNumber;

    //previous registration, it is used when the device is re-registered
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinColumn(name = "old_equipment_id", insertable = false, updatable = false)
    private Equipment oldEquipment;

    @Column(name = "old_equipment_id")
    private UUID oldEquipmentId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> parameters;


//    private String boomLength; //strelasining uzunligi (kran)
//
//    private String liftingCapacity; //yuk ko'tarishi (kran, lift, yuk ko'targich)
//
//    private String capacity; // hajmi (sosud, kimyoviy idish, qozon, sug, 100ming)
//
//    private String environment; // muhit (sosud, quvur, qozon, sug)
//
//    private String pressure; // ruxsat etilgan bosim (sosud, quvur, kimyoviy idish, bug' va issiq suv quvuri, qozon, sug, 100ming)

    @Enumerated(EnumType.STRING)
    private Sphere sphere;  // foydalanish sohasi (lift)

//    private String stopCount; // to'xtashlar soni (lift)
//
//    private String length; // uzunligi (eskalator, osma yo'l, quvur, bug' va issiq suv quvuri)
//
//    private String speed; // tezligi (eskalator, osma yo'l)
//
//    private String height; // ko'tarish balandligi (eskalator, yuk ko'targich)
//
//    private String passengersPerMinute; // o'tkazish qobilyati (eskalator)
//
//    private String passengerCount; // harakatlanuvchi sostav soni (osma yo'l)
//
//    private String diameter; // diametr (quvur, bug' va issiq suv quvuri)
//
//    private String thickness; // devor qalinligi (quvur, bug' va issiq suv quvuri)
//
    private String attractionName; // attraksion nomi (attraksion)

    private LocalDate acceptedAt; // foydalanishga qabul qilingan sana (attraksion pasporti)

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipmentSort.class)
    @JoinColumn(name = "child_equipment_sort_id", insertable = false, updatable = false)
    private ChildEquipmentSort childEquipmentSort; // attratsion tipi (attraksion)

    @Column(name = "child_equipment_sort_id")
    private Integer childEquipmentSortId;   // attratsion tipi (attraksion)

    private String country; // ishlab chiqargan mamlakat (attraksion)

    private Integer servicePeriod; // hizmat muddati (attraksion pasporti)

    private RiskLevel riskLevel; // havf darajasi (attraksion pasporti)

    private String parentOrganization; // yuqori turuvchi tashkilot (attraksion)

    private LocalDate nonDestructiveCheckDate; // putur yetkazmaydigan nazoratda ko'rikdan o'tkazish (sosud, bug'qozon, quvur, osma yo'l, kimyoviy idish, qozon, sug, )

//    private String temperature; //temperatura (bug' va issiq suv quvuri, qozon)
//
//    private String density; // zichligi (qozon)
//
//    private String fuel; // yoqilg'i (100 ming)

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinColumn(name = "attraction_passport_id", insertable = false, updatable = false)
    private Equipment attractionPassport; // faqat atraksionni ro'yhatga olish uchun

    @Column(name = "attraction_passport_id")
    private UUID attractionPassportId; // faqat atraksionni ro'yhatga olish uchun

//    @Column(nullable = false)
//    private String labelPath; // birka rasmi

    private String description;

    @Column(nullable = false)
    String inspectorName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> files;

    private String registryFilePath; // Reestr file path

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private String legalAddress;

//    @Column(nullable = false)
    private Boolean isActive;

//    @Column(nullable = false)
//    private String saleContractPath; // oldi-sotdi shartnomasi
//
//    @Column(nullable = false)
//    private String equipmentCertPath; // qurilma sertifikati
//
//    @Column(nullable = false)
//    private String assignmentDecreePath; // maxsus tayinlash buyrug'i
//
//    private String expertisePath; //ekspertiza fayli
//
//    private String installationCertPath; //montaj guvohnomasi
//
//    private String additionalFilePath; // qo'shimcha ma'lumotlar fayli
//
//    private String passportPath; // pasporti fayli (attraksion pasporti, atraksion)
//
//    private String techReadinessActPath; // texnik tayyorlilik dalolatnomasi (attraksion pasporti)
//
//    private String seasonalReadinessActPath; // mavsumiy tayyorlilik dalolatnomasi (attraksion pasporti)
//
//    private String safetyDecreePath; // havfsiz foydalanish bo'yicha masul shaxs buyrug'i (attraksion pasporti)
//
//    private String gasSupplyProjectPath; // gaz ta'minoti loyihasi (100ming)
//
//    private String technicalManualPath; //texnik foydalanish qo'llanmasi (attraksion)
//
//    private String serviceManualPath; //xizmat ko'rsatish va ta'mirlash qo'llanmasi (atraksion)
//
//    private String technicalJournalPath; //texnik jurnal nusxasi (atraksion)
//
//    private String acceptanceFilePath; //qabul qilinganligi xujjatlari (atraksion)
//
//    private String routeInfoPath; //marshrut ma'lumotlari (atraksion)
//
//    private String conformityCertPath; //muvofiqlik sertifikati(atraksion)
//
//    private String safetyUsageReportPath; //xavfsiz foydalanish xulosasi (atraksion)
//
//    private String insurancePolicyPath; //sug'urta polisi (atraksion)
//
//    private String biomechanicalRiskFilePath; //beomexanik xavf hujjati (atraksion)
//
//    private String technicalStatusActPath; //texnik holat dalolatnomasi (atraksion)
//
//    private String usageRightsPath; //foydalanish huquqi (atraksion)
//
//    private String acceptanceCertPath; //foydalanishga qabul qilish guvohnomasi (atraksion)
}
