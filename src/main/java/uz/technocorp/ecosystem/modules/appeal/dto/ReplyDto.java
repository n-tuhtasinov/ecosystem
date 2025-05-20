package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 19.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

    @NotNull(message = "Ariza ID si jo'natilmadi")
    private UUID appealId;

    @NotBlank(message = "Xulosa qismi yuborilmadi")
    private String conclusion;

}
