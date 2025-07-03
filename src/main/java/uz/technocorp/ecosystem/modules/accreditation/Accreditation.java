package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Accreditation extends BaseEntity {

    @Column(nullable = false)
    private Long tin;

    @Enumerated(EnumType.STRING)
    private AccreditationType type;

    private Long customerTin;

    @Enumerated(EnumType.STRING)
    private List<AccreditationSphere> accreditationSpheres;

    @Column(nullable = false, unique = true)
    private String certificateNumber;
    @Column(nullable = false)
    private LocalDate certificateValidityDate;
    @Column(nullable = false)
    private LocalDate certificateDate;

    private String assessmentCommissionDecisionPath;
    private LocalDate assessmentCommissionDecisionDate;
    private String assessmentCommissionDecisionNumber;

    private String accreditationCommissionDecisionPath;
    private LocalDate accreditationCommissionDecisionDate;
    private String accreditationCommissionDecisionNumber;

    private String referencePath;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;


    private LocalDate submissionDate;
    private LocalDate monitoringLetterDate;
    private String monitoringLetterNumber;
    private String expertiseObjectName;
    private String firstSymbolsGroup;
    private String secondSymbolsGroup;
    private String thirdSymbolsGroup;
    private String objectAddress;
    private String customerLegalName;
    private String customerLegalForm;
    private String customerLegalAddress;
    private String customerPhoneNumber;
    private String customerFullName;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    private String expertiseConclusionPath;
    private String expertiseConclusionNumber;
    private LocalDate expertiseConclusionDate;

}
