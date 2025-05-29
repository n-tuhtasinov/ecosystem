package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReport;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.enums.InspectionReportExecutionStatus;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class InspectionReportExecution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = InspectionReport.class)
    @JoinColumn(name = "report_id", insertable = false, updatable = false)
    private InspectionReportDto report;

    @Column(name = "report_id")
    private UUID reportId;

    private String fixedBugsFilePath;

    private LocalDate fileUploadDate;

    @Enumerated(EnumType.STRING)
    private InspectionReportExecutionStatus status;

    @Column(columnDefinition = "text")
    private String rejectedCause;
}
