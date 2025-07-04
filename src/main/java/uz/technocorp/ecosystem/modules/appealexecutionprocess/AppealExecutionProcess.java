package uz.technocorp.ecosystem.modules.appealexecutionprocess;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.shared.BaseEntity;
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
public class AppealExecutionProcess extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class, optional = false)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id", nullable = false)
    private UUID appealId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealStatus appealStatus;

    @Column(columnDefinition = "text")
    private String description;

    public AppealExecutionProcess(UUID appealId, AppealStatus appealStatus, String description) {
        this.appealId = appealId;
        this.appealStatus = appealStatus;
        this.description = description;
    }
}
