package uz.technocorp.ecosystem.modules.attestation;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
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
public class Attestation extends BaseEntity {

    @Column(nullable = false)
    private String employeePin;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeLevel employeeLevel;

    @Column
    private LocalDate dateOfAttestation;

    @Column
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id", nullable = false)
    private UUID appealId;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String legalAddress;

    @Column(nullable = false)
    private UUID hfId;

    @Column(nullable = false)
    private String hfName;

    @Column(nullable = false)
    private String hfAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AttestationStatus status;

    @Column(nullable = false)
    private Integer regionId;

    @Column(nullable = false)
    private Integer districtId;

    @Column
    private UUID executorId;

    @Column
    private String reportPdfPath;
}
