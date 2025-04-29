package uz.technocorp.ecosystem.modules.appeal.dto;

import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
public interface SignedAppealDto {
    String getFilePath();

    String getSign();

    Object getDto();

    DocumentType getType();
}
