package uz.technocorp.ecosystem.modules.assigninspectorirs;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AssignInspectorIrs extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "inspector_id", insertable = false, updatable = false)
    private User inspector;

    @Column(name = "inspector_id")
    private UUID inspectorId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = IonizingRadiationSource.class)
    @JoinColumn(name = "irs_id", insertable = false, updatable = false)
    private IonizingRadiationSource irs;

    @Column(name = "irs_id")
    private UUID irsId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RiskAnalysisInterval.class)
    @JoinColumn(name = "interval_id", insertable = false, updatable = false)
    private RiskAnalysisInterval interval;

    @Column(name = "interval_id")
    private Integer intervalId;
}
