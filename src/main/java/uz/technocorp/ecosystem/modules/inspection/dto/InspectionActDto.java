package uz.technocorp.ecosystem.modules.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InspectionActDto {

    @NotNull(message = "Tekshiruv ID si jo'natilmadi")
    private UUID inspectionId;

    @NotBlank(message = "Xulosa qismi yuborilmadi")
    private String conclusion;
}
