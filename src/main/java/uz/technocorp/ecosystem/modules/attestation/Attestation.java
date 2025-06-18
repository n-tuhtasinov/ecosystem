package uz.technocorp.ecosystem.modules.attestation;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attestation extends BaseEntity {

    @Column(nullable = false)
    private String employeePin;

    @Column(nullable = false)
    private String employeeName;

    @Column
    private String employeePosition;

    @Column
    private LocalDate expiryDate;



}
