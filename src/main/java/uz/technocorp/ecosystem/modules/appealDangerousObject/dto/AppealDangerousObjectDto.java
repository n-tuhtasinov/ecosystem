package uz.technocorp.ecosystem.modules.appealDangerousObject.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public record AppealDangerousObjectDto(

        String phoneNumber,
        String email,
        String upperOrganization,
        String name,
        String address,
        Integer dangerousObjectTypeId,
        String extraArea,
        String description,
        String objectNumber,
        Integer appealTypeId,
        String number,
        String orderNumber,
        Integer regionId,
        Integer districtId,
        String identificationCardPath,
        String receiptPath
) {
}
