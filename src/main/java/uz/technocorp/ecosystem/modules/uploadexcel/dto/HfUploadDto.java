package uz.technocorp.ecosystem.modules.uploadexcel.dto;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */

public record HfUploadDto(

        LocalDate registrationDate,

        String registryNumber,

        String pin,

        String upperOrganization,

        String tin,

        String hfName,

        Integer regionSoato,

        Integer districtSoato,

        String address,

        String hfTypeName,

        String extraArea,

        String hazardousSubstance,

        String spheres,

        String description,

        Integer rowNumber//for error tracking
) {
}
