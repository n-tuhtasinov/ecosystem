package uz.technocorp.ecosystem.modules.checklist;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;
import uz.technocorp.ecosystem.modules.checklisttemplate.ChecklistTemplate;
import uz.technocorp.ecosystem.modules.profile.Profile;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Checklist extends AuditAndIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    private Long tin;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChecklistTemplate.class)
    @JoinColumn(name = "template_id", updatable = false, insertable = false)
    private ChecklistTemplate template;

    @Column(name = "template_id")
    private Integer templateId;

    private String path;

}
