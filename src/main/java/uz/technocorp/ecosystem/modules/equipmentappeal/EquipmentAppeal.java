package uz.technocorp.ecosystem.modules.equipmentappeal;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.03.2025
 * @since v1.0
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAppeal extends AuditAndIdEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealStatus status;

    private LocalDate deadline;

}
