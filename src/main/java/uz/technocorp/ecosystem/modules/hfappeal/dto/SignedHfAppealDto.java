package uz.technocorp.ecosystem.modules.hfappeal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignedHfAppealDto implements SignedAppealDto {

    @NotBlank(message = "File path kiritilmadi!")
    private String filePath;

    @NotBlank(message = "Imzolangan malumot kiritilmadi")
    private String sign;

    @NotNull(message = "XICHO arizasi ma'lumotlari jo'natilmadi")
    private @Valid HfAppealDto dto;

    @Override
    public DocumentType getType() {
        return DocumentType.APPEAL;
    }
}