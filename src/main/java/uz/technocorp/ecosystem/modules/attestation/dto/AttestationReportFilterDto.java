package uz.technocorp.ecosystem.modules.attestation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttestationReportFilterDto {
    private String legalName;
    private Long legalTin;
    private String hfName;
    private Integer regionId;
    private UUID appealId;
    private UUID executorId;
    private AttestationStatus status;
    private Integer direction;
}