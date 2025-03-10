package uz.technocorp.ecosystem.modules.dangerousobject;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appealdangerousobject.AppealDangerousObject;
import uz.technocorp.ecosystem.modules.dangerousobjecttype.DangerousObjectType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

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
public class DangerousObject extends AuditEntity {

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

    @OneToOne(fetch = FetchType.LAZY, targetEntity = AppealDangerousObject.class)
    @JoinColumn(name = "registration_appeal_id", insertable = false, updatable = false)
    private AppealDangerousObject appeal;

    @Column(name = "registration_appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DangerousObjectType.class)
    @JoinColumn(name = "dangerous_object_type_id", insertable = false, updatable = false)
    private DangerousObjectType dangerousObjectType;

    @Column(name = "dangerous_object_type_id")
    private Integer dangerousObjectTypeId;

    private String extraArea;

    private String description;

    private String objectNumber;
}
