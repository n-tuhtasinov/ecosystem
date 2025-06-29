package uz.technocorp.ecosystem.modules.cadastrepassport.view;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public record CadastrePassportViewById(
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
