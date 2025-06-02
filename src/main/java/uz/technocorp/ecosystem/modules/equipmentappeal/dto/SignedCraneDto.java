package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 30.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignedCraneDto implements SignedEquipmentDto {

    @NotNull(message = "XICHO arizasi ma'lumotlari jo'natilmadi")
    private @Valid CraneDto dto;

    @NotBlank(message = "File path kiritilmadi!")
    private String filePath;

    @NotBlank(message = "Imzolangan malumot kiritilmadi")
    private String sign;

    @Override
    public DocumentType getType() {
        return null;
    }
}
