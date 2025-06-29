package uz.technocorp.ecosystem.modules.declaration.view;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public record DeclarationView(
        UUID id,
        String registryNumber,
        LocalDateTime createdAt,
        String hfRegistryNumber,
        String hfName,
        String hfAddress,
        String producingOrganizationName,
        Long producingOrganizationTin
) {
}
