package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;

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
public class CraneDto extends EquipmentAppealDto implements AppealDto{

    @NotBlank(message = "Strelasining uzunligi jo'natilmadi")
    private String boomLength;

    @NotBlank(message = "Yuk ko'tara olish qiymati jo'natilmadi")
    private String liftingCapacity;

//    @NotBlank(message = "Qurilmaning birkasi bilan surati pathi jo'natilmadi")
//    private String labelPath;

//    @NotBlank(message = "Qurilmaning oldi-sotdi shartnomasi pathi jo'natilmadi")
//    private String agreementPath;

//    @NotBlank(message = "Qurilmaning sertifikati fayli pathi jo'natilmadi")
//    private String equipmentCertPath;

//    @NotBlank(message = "Qurilmaning mas'ul shaxs tayinlanganligi to'g'risidagi buyrug'i pathi jo'natilmadi")
//    private String assignmentDecreePath;

//    @NotBlank(message = "Qurilmaning expertiza loyihasi pathi jo'natilmadi")
//    private String expertisePath;

//    @NotBlank(message = "Qurilmaning montaj guvohnomasi fayli pathi jo'natilmadi")
//    private String installationCertPath;

//    private String additionalFilePath;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CRANE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
