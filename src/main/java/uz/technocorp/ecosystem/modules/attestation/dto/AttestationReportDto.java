package uz.technocorp.ecosystem.modules.attestation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttestationReportDto {

    private String legalName;
    private Long legalTin;
    private String legalAddress;
    private String hfName;
    private String hfAddress;
    private Integer totalEmployees;
    private Integer leadersPassed;
    private Integer techniciansPassed;
    private Integer employeesPassed;
    private Integer failedEmployees;
}