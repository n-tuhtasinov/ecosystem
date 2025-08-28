package uz.technocorp.ecosystem.modules.irs.view;

import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.06.2025
 * @since v1.0
 */
public record IrsViewById(
        String parentOrganization,
        String address,
        String supervisorName,
        String supervisorPosition,
        String supervisorStatus,
        String supervisorEducation,
        String supervisorPhoneNumber,
        String division,
        IrsIdentifierType identifierType,
        String symbol,
        String sphere,
        String factoryNumber,
        String serialNumber,
        String activity,
        String type,
        IrsCategory category,
        String country,
        LocalDate manufacturedAt,
        String acceptedFrom,
        LocalDate acceptedAt,
        Boolean isValid,
        IrsUsageType usageType,
        String storageLocation,
        Map<String, String> files,
        UUID appealId,
        String registryNumber,
        UUID profileId,
        Long legalTin,
        LocalDate registrationDate,
        String inspectorName
) {}
