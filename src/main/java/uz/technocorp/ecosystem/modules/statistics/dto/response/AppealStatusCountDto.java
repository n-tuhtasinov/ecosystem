package uz.technocorp.ecosystem.modules.statistics.dto.response;

import lombok.*;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppealStatusCountDto {
    private String officeName;
    private Integer total;
    private Integer totalPct;
    private Integer inProcess;
    private Integer inProcessPct;
    private Integer inAgreement;
    private Integer inAgreementPct;
    private Integer inApproval;
    private Integer inApprovalPct;
    private Integer completed;
    private Integer completedPct;
    private Integer canceled;
    private Integer canceledPct;
    private Integer rejected;
    private Integer rejectedPct;
}
