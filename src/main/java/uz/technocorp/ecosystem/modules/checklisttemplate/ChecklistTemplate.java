package uz.technocorp.ecosystem.modules.checklisttemplate;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.AuditEntity;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChecklistTemplate extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String name;

    @Column(nullable = false)
    private String path;

}
