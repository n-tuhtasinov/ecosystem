package uz.technocorp.ecosystem.modules.accreditation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccreditationRejectionDto {

    @NotNull(message = "Ariza Idsi yuborilmadi!")
    private UUID appealId;

    private String rejectionReason;
}
