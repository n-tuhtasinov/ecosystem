package uz.technocorp.ecosystem.modules.appeal;

import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.user.User;

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
public class Appeal extends AuditEntity {

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

    @Column(nullable = false)
    private UUID mainId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "inspector_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "inspector_id")
    private UUID inspectorId;

    public Appeal(AppealType appealType, String number, String orderNumber, Long legal_tin, String legalName, Integer regionId, String regionName, Integer districtId, String districtName, UUID profileId, UUID mainId) {
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
        this.mainId = mainId;
    }
}
