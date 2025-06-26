package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
public class ReplyAttestationDto {

    @NotNull(message = "Ariza ID si jo'natilmadi")
    private UUID appealId;

    @NotNull(message = "Attestatsiya ijro muddati kiritilmadi")
    private LocalDateTime dateOfAttestation;

    @NotBlank(message = "Xulosa qismi yuborilmadi")
    private String resolution;
}
