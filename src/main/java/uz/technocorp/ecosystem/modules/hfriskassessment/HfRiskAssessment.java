package uz.technocorp.ecosystem.modules.hfriskassessment;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.BaseEntity;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HfRiskAssessment extends BaseEntity {

    private short tin;

    private int sumScore;

    @Column(nullable = false)
    private String objectName;
}
