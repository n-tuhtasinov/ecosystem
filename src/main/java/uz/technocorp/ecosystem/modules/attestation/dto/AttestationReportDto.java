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
    private Long totalEmployees;
    private Long leadersPassed;
    private Long techniciansPassed;
    private Long employeesPassed;
    private Long failedEmployees;
}