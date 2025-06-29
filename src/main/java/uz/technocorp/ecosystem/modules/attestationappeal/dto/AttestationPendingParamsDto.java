package uz.technocorp.ecosystem.modules.attestationappeal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 25.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttestationPendingParamsDto {

    private String search;
    private Integer regionId;
    private UUID inspectorId;
    private Integer page = 1;
    private Integer size = 10;
}
