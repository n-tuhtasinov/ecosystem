package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 30.05.2025
 * @since v1.0
 */
public interface SignedEquipmentDto {

    String getFilePath();

    String getSign();

    EquipmentAppealDto getDto();

    @Schema(hidden = true)
    DocumentType getType();
}
