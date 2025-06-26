package uz.technocorp.ecosystem.modules.employee;


import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

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
public class Employee extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String pin;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String profession;

    @Column
    private String certNumber;

    @Column
    private LocalDate dateOfEmployment;

    @Column
    private LocalDate certDate;

    @Column
    private LocalDate certExpiryDate;

    @Column
    private LocalDate ctcTrainingFromDate; // Kontexnazorat o'qigan muddati

    @Column
    private LocalDate ctcTrainingToDate; // Kontexnazorat o'quv muddati

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeLevel level;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hf_id", insertable = false, updatable = false)
    private HazardousFacility hf;

    @Column(name = "hf_id")
    private UUID hfId;

    @Column
    private String hfName;

}
