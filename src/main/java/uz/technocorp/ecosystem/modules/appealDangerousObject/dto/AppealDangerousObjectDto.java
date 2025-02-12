package uz.technocorp.ecosystem.modules.appealDangerousObject.dto;

import java.util.UUID;

public record AppealDangerousObjectDto(

        String legalAddress,
        String phoneNumber,
        String email,
        String upperOrganization,
        String name,
        String address,
        UUID appealId,
        Integer dangerousObjectTypeId,
        String extraArea,
        String description,
        String objectNumber,
        Integer appealTypeId,
        String number,
        String orderNumber,
        String legal_tin,
        String legalName,
        Integer regionId,
        Integer districtId
) {
}
