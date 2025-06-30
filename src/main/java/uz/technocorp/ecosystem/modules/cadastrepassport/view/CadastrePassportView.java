package uz.technocorp.ecosystem.modules.cadastrepassport.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public record CadastrePassportView(
        UUID id,
        LocalDateTime createdAt,
        String registryNumber,
        String legalName,
        Long legalTin,
        String legalAddress,
        String hfName,
        String hfAddress
) {
}
