package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CraneDto extends EquipmentAppealDto {

    @NotBlank(message = "Strelasining uzunligi jo'natilmadi")
    private String boomLength;

    @NotBlank(message = "Yuk ko'tara olish qiymati jo'natilmadi")
    private String liftingCapacity;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CRANE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

//    public CraneDto(String phoneNumber, UUID hazardousFacilityId, Integer childEquipmentId, String factoryNumber, Integer regionId, Integer districtId, String address, String model, String factory, String location, LocalDate manufacturedAt, LocalDate partialCheckDate, LocalDate fullCheckDate, String labelPath, String saleContractPath, String equipmentCertPath, String assignmentDecreePath, String expertisePath, String installationCertPath, String additionalFilePath, String boomLength, String liftingCapacity) {
//        super(phoneNumber, hazardousFacilityId, childEquipmentId, factoryNumber, regionId, districtId, address, model, factory, location, manufacturedAt, partialCheckDate, fullCheckDate, labelPath, saleContractPath, equipmentCertPath, assignmentDecreePath, expertisePath, installationCertPath, additionalFilePath);
//        this.boomLength = boomLength;
//        this.liftingCapacity = liftingCapacity;
//    }

}
