package uz.technocorp.ecosystem.modules.attestation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 24.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttestationConductDto {

    @NotNull(message = "Ariza ID yuborilmadi")
    private UUID appealId;

    @NotBlank(message = "Attestatsiya bayonnoma fayli yuborilmadi")
    private String filePath;

    @NotNull(message = "Attestatsiya sanasi kiritilmadi")
    private LocalDate dateOfAttestation;

    @NotNull(message = "Attestatsiya natijalari kiritilmadi")
    private Map<String, AttestationStatus> result;
}
