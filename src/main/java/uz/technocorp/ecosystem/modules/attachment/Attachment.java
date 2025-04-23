package uz.technocorp.ecosystem.modules.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;

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
public class Attachment extends AuditAndIdEntity {

    @Column(unique = true, nullable = false)
    private String path;

    @Column(columnDefinition = "text")
    private String htmlContent;

    public Attachment(String path) {
        this.path = path;
    }
}
