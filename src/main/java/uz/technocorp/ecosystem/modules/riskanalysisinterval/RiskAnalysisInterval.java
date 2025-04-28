package uz.technocorp.ecosystem.modules.riskanalysisinterval;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;

import java.time.LocalDate;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskAnalysisInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskAnalysisIntervalStatus status;
}
