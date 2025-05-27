package uz.technocorp.ecosystem.modules.document;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.document.dto.Signer;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

import java.util.List;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String path;

    @Column(columnDefinition = "text")
    private String signedContent;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Signer> signers;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private Boolean isConfirmed;

    private String description;
}
