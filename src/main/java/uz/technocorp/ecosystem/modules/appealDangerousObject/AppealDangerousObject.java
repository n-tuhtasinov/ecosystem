package uz.technocorp.ecosystem.modules.appealDangerousObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.dangerousObjectType.DangerousObjectType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppealDangerousObject extends AuditEntity {


    private String legalAddress;

    private String phoneNumber;

    private String email;

    private String upperOrganization;

    private String name;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DangerousObjectType.class)
    @JoinColumn(name = "dangerous_object_type_id", insertable = false, updatable = false)
    private DangerousObjectType dangerousObjectType;

    @Column(name = "dangerous_object_type_id")
    private Integer dangerousObjectTypeId;

    private String extraArea;

    private String description;

    private String objectNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "identification_card_id", insertable = false, updatable = false)
    private Attachment identificationCard;

    @Column(name = "identification_card_id")
    private UUID identificationCardId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Attachment.class)
    @JoinColumn(name = "receipt_id", insertable = false, updatable = false)
    private Attachment receipt;

    @Column(name = "receipt_id")
    private UUID receiptId;

}
