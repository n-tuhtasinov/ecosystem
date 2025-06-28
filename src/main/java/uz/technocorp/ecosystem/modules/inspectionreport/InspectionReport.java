package uz.technocorp.ecosystem.modules.inspectionreport;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.enums.InspectionReportExecutionStatus;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class InspectionReport extends BaseEntity {

    @Column(columnDefinition = "text")
    private String defect;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Inspection.class)
    @JoinColumn(name = "inspection_id", insertable = false, updatable = false)
    private Inspection inspection;

    @Column(name = "inspection_id")
    private UUID inspectionId;

    private boolean eliminated;

    private LocalDate deadline;
}
