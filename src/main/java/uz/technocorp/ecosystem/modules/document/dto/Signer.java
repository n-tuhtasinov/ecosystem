package uz.technocorp.ecosystem.modules.document.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 29.04.2025
 * @since v1.0
 */
public record Signer(
        String signedBy,
        UUID executorId,
        LocalDateTime createdAt,
        Boolean isSigned
) implements Serializable {
}
