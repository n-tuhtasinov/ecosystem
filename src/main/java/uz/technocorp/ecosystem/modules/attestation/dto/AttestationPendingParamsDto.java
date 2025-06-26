package uz.technocorp.ecosystem.modules.attestation.dto;

import lombok.*;

/**
 * @author Suxrob
 * @version 1.0
 * @created 25.06.2025
 * @since v1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttestationPendingParamsDto {

    private String search; // legalTin or legalName
    private Integer regionId;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

}
