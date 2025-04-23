package uz.technocorp.ecosystem.modules.appealexecutionprocess;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppealExecutionProcess extends AuditAndIdEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @Column(columnDefinition = "text")
    private String description;

    public AppealExecutionProcess(UUID appealId, String description) {
        this.appealId = appealId;
        this.description = description;
    }
}
