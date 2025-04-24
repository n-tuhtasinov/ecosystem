package uz.technocorp.ecosystem.modules.document;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Document extends AuditAndIdEntity {

    @Column(nullable = false, unique = true)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private boolean agreed;

    public Document(String path, UUID appealId, DocumentType documentType, boolean agreed) {
        this.path = path;
        this.appealId = appealId;
        this.documentType = documentType;
        this.agreed = agreed;
    }
}
