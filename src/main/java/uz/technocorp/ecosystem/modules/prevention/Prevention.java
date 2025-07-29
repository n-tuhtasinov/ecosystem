package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Prevention extends BaseEntity {

    @Column(nullable = false)
    private Integer typeId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String preventionFilePath;

    @Column
    private String eventFilePath;

    @Column
    private String organizationFilePath;

    @Column(nullable = false)
    private Boolean viewed;

    @Column
    private LocalDateTime viewDate;

    @Column(nullable = false)
    private String inspectorName;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_tin", referencedColumnName = "identity", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_tin")
    private Long profileTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String legalAddress;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(targetEntity = District.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;
}
