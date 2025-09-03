package uz.technocorp.ecosystem.modules.hf.view;

import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;
import uz.technocorp.ecosystem.shared.dto.FileDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.06.2025
 * @since v1.0
 */
public record HfViewById(
        Long legalTin,
        String legalName,
        LocalDate registrationDate,
        String registryNumber,
        UUID profileId,
        String upperOrganization,
        String name,
        String address,
        String location,
        String hazardousSubstance,
        UUID appealId,
        Integer hfTypeId,
        String hfTypeName,
        String extraArea,
        String description,
        List<HFSphere> spheres,
        String deregisterReason,
        String deregisterFilePath,
        String periodicUpdateReason,
        String periodicUpdateFilePath,
        boolean active,
        Map<String, FileDto> files,
        String registryFilePath,
        String inspectorName,
        String mode
) {
}
