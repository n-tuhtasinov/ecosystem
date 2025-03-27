package uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.dto;


/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public record HazardousFacilityRegistrationAppealDto(

        String phoneNumber,
        String email,
        String upperOrganization,
        String name,
        String address,
        Integer hazardousFacilityTypeId,
        String extraArea,
        String description,
        String appealType,
        Integer regionId,
        Integer districtId,
        String identificationCardPath,
        String receiptPath
) {
}
