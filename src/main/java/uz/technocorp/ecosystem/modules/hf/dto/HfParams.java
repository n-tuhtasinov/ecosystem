package uz.technocorp.ecosystem.modules.hf.dto;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public record HfParams(
        Integer page,
        Integer size,
        Long legalTin,
        String registryNumber,
        Integer regionId,
        Integer districtId,
        LocalDate startDate,
        LocalDate endDate
) {
}
