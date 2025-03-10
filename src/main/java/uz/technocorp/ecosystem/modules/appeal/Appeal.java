package uz.technocorp.ecosystem.modules.appeal;

import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;
import jakarta.persistence.*;
import uz.technocorp.ecosystem.modules.user.User;

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
public class Appeal extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private Long legalTin;

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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Office.class)
    @JoinColumn(name = "office_id", insertable = false, updatable = false)
    private Office office;

    @Column(name = "office_id")
    private Integer officeId;

    @Column(nullable = false)
    private String officeName;

    @Column(nullable = false)
    private UUID mainId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "inspector_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "inspector_id")
    private UUID inspectorId;

    private String inspectorName;

    @Enumerated(EnumType.STRING)
    private AppealStatus status;

    private String address;

    private String email;

    private String phoneNumber;

    private LocalDate deadline;

    private LocalDate date;

    public Appeal(AppealType appealType, String number, String orderNumber, Long legalTin, String legalName,
                  Integer regionId, String regionName, Integer districtId, String districtName, UUID profileId,
                  Integer officeId, String officeName, UUID mainId, String address, String email, String phoneNumber, LocalDate date) {
        this.appealType = appealType;
        this.number = number;
        this.orderNumber = orderNumber;
        this.legalTin = legalTin;
        this.legalName = legalName;
        this.regionId = regionId;
        this.regionName = regionName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.profileId = profileId;
        this.officeId = officeId;
        this.officeName = officeName;
        this.mainId = mainId;
        this.address = address;
        this.email = address;
        this.phoneNumber = address;
        this.date = date;
        this.status = AppealStatus.New;
    }
}
