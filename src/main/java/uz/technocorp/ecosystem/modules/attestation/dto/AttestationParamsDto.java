package uz.technocorp.ecosystem.modules.attestation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttestationParamsDto {

    private String search;
    private String hfName;
    private Integer regionId;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

}