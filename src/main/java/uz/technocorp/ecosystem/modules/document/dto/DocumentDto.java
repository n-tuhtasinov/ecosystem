package uz.technocorp.ecosystem.modules.document.dto;

import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @description belongId indicates which class this document is based on.
 * @since v1.0
 */
public record DocumentDto(
        UUID belongId,
        DocumentType documentType,
        String path,
        String sign,
        String ip,
        UUID singerId,
        List<UUID> executorIds,
        AgreementStatus status) {
}
