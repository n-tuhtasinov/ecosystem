package uz.technocorp.ecosystem.modules.inspection;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.document.Document;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 22.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Inspection extends BaseEntity {

    private Long tin;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RiskAnalysisInterval.class)
    @JoinColumn(name = "interval_id", insertable = false, updatable = false)
    private RiskAnalysisInterval interval;

    @Column(name = "interval_id")
    private Integer intervalId;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "inspection_inspector",
    joinColumns = @JoinColumn(name = "inspection_id"),
    inverseJoinColumns = @JoinColumn(name = "inspector_id", insertable = false, updatable = false))
    private List<User> inspectors;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = UUID.class)
    @CollectionTable(name = "inspection_inspector", joinColumns = @JoinColumn(name = "inspection_id"))
    @Column(name = "inspector_id")
    private List<UUID> inspectorIds;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
    @CollectionTable(name = "inspection_region", joinColumns = @JoinColumn(name = "inspection_id"))
    @Column(name = "region_id")
    private Set<Integer> regionIds;

    @ManyToOne(targetEntity = District.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    @Enumerated(EnumType.STRING)
    private InspectionStatus status;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Document decree;
//
//    private String decreePath;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Document act;
//
//    private String actPath;

    private String specialCode;

    private String schedulePath;
    private String notificationLetterPath;
    private LocalDate notificationLetterDate;
    private String orderPath;
    private String programPath;
    private String measuresPath;
    private String resultPath;
}
