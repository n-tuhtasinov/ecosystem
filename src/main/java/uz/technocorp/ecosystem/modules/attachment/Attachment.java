package uz.technocorp.ecosystem.modules.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AuditEntity {

    @Column(unique = true, nullable = false)
    private String path;

}
