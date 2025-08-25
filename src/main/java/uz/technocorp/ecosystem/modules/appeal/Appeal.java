package uz.technocorp.ecosystem.modules.appeal;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealMode;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
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
public class Appeal extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false, unique = true)
    private Long orderNumber;

    @Column(nullable = false)
    private Long ownerIdentity;

    @Column(nullable = false)
    private String ownerName;

    @Column
    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class, optional = false)
    @JoinColumn(name = "owner_region_id", insertable = false, updatable = false)
    private Region ownerRegion;

    @Column(name = "owner_region_id")
    private Integer ownerRegionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class, optional = false)
    @JoinColumn(name = "owner_district_id", insertable = false, updatable = false)
    private District ownerDistrict;

    @Column(name = "owner_district_id")
    private Integer ownerDistrictId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(nullable = false)
    private String ownerAddress;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Office.class)
    @JoinColumn(name = "office_id", insertable = false, updatable = false)
    private Office office;

    @Column(name = "office_id")
    private Integer officeId;

    private String officeName;

    private Integer departmentId;

    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "executor_id", insertable = false, updatable = false)
    private User executor;

    @Column(name = "executor_id")
    private UUID executorId;

    private String executorName; //ijrosini ta'minlovchi shaxs (inspektor yoki qo'mita hodimi)

    private String approverName; // hujjatni kelishib beruvchi shaxs (hududiy bo'lim boshlig'i)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealStatus status;

    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    private LocalDate deadline;

    @Column
    private String resolution; // Hududiy boshqarma boshlig'i rezolyutsiyasi

    @Column(columnDefinition = "text")
    private String conclusion; // Inspector va reject qilish xulosasi

    @Column(columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode data;

    @Column(nullable = false)
    private Boolean isRejected;

    @Column
    @Enumerated(EnumType.STRING)
    private AppealMode mode;

}
