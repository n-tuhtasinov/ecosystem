package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.03.2025
 * @since v1.0
 * @description IRS - the abbreviation of 'Ionizing Radiation Sources'
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IrsAppeal extends AuditEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppealStatus status;

    private LocalDate deadline;

//    @Column(columnDefinition = "jsonb", nullable = false)
//    private JsonNode data;

}
