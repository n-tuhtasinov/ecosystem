package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSort;
import uz.technocorp.ecosystem.modules.childequipmenttype.ChildEquipmentType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.03.2025
 * @since v1.0
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAppeal extends AuditEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hazardous_facility_id", insertable = false, updatable = false)
    private HazardousFacility hazardousFacility;

    @Column(name = "hazardous_facility_id")
    private UUID hazardousFacilityId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipmentType.class, optional = false)
    @JoinColumn(name = "child_equipment_type_id", insertable = false, updatable = false)
    private ChildEquipmentType childEquipmentType;

    @Column(name = "child_equipment_type_id", nullable = false)
    private Integer childEquipmentTypeId;

    @Column(nullable = false)
    private String factoryNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class, optional = false)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id", nullable = false)
    private Integer regionId;

    @Column(nullable = false)
    private String regionName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class, optional = false)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(nullable = false)
    private String districtName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String factory;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private Long lng;

    @Column(nullable = false)
    private Long lat;

    @Column(nullable = false)
    private LocalDate manufacturedAt;

    @Column(nullable = false)
    private LocalDate partialCheckDate;

    @Column(nullable = false)
    private LocalDate fullCheckDate;

    //previous registration number.
    //it is used when the device is re-registered
    @Column
    private String oldNumber;

    private Double boomLength; //strelasining uzunligi (kran)

    private Double liftingCapacity; //yuk ko'tarishi (kran, lift, yuk ko'targich)

    private Double capacity; // hajmi (sosud)

    private Double environment; // muhit (sosud, quvur)

    private Double pressure; // ruxsat etilgan bosim (sosud, quvur)

    @Enumerated(EnumType.STRING)
    private Sphere sphere;  // foydalanish sohasi (lift)

    private Integer stopCount; // to'xtashlar soni (lift)

    private Double length; // uzunligi (eskalator, osma yo'l, quvur)

    private Double speed; // tezligi (eskalator, osma yo'l)

    private Double height; // ko'tarish balandligi (eskalator, yuk ko'targich)

    private Integer passengersPerMinute; // o'tkazish qobilyati (eskalator)

    private Integer passengerCount; // karakatlanuvchi sostav soni (osma yo'l)

    private Double diameter; // diametr (quvur)

    private Double thickness; // devor qalinligi (quvur)

    private String rideName; // attraksion nomi (attraksion)

    private LocalDate acceptedAt; // foydalanishga qabul qilingan sana (attraksion)

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipmentSort.class)
    @JoinColumn(name = "child_equipment_sort_id", insertable = false, updatable = false)
    private ChildEquipmentSort childEquipmentSort; // attratsion tipi (attraksion)

    @Column(name = "child_equipment_sort_id")
    private Integer childEquipmentSortId;   // attratsion tipi (attraksion)

    private String country; // ishlab chiqargan mamlakat (attraksion)

    private Integer servicePeriod; // hizmat muddati (attraksion)

    private RiskLevel riskLevel; // havf darajasi (attraksion)











    @Column(nullable = false)
    private String labelPath; // birka rasmi

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "inspector_id", insertable = false, updatable = false)
    private Profile inspector;

    @Column(name = "inspector_id")
    private UUID inspectorId;

    @Column(nullable = false)
    private String agreementPath; // oldi-sotdi kelishuvi shartnomasi

    @Column(nullable = false)
    private String equipmentCertPath; // qurilma sertifikati

    @Column(nullable = false)
    private String assignmentDecreePath; // maxsus tayinlash buyrug'i

    private String expertisePath; //ekspertiza fayli

    private String installationCertPath; //montaj guvohnomasi

    @Column(nullable = false)
    private String appealPath; //ariza fayli

    private String additionalFilePath; // qo'shimcha ma'lumotlar fayli

    private String passportPath; // pasporti fayli (attraksion)

    private String techReadinessActPath; // texnik tayyorlilik dalolatnomasi (attraksion)

    private String seasonalReadinessActPath; // mavsumiy tayyorlilik dalolatnomasi (attraksion)

    private String safetyDecreePath; // havfsiz foydalanish bo'yicha masul shaxs buyrug'i (attraksion)

}
