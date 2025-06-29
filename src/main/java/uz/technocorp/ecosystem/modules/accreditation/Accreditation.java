package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
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

}
