package uz.technocorp.ecosystem.modules.appeal.dto.hf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HfDeregisterAppealDto implements AppealDto {

        @NotNull(message = "XICHO ID raqami jo'natilmadi")
        private UUID hazardousFacilityId;

        @NotBlank(message = "Telefon raqami kiritilmadi!")
        private String phoneNumber;

        @NotBlank(message = "Pochta manzili kiritilmadi!")
        private String email;

        @NotNull(message = "Viloyat jo'natilmadi")
        private Integer regionId;

        @NotNull(message = "Tuman jo'natilmadi")
        private Integer districtId;

        @NotBlank(message = "Manzil jo'natilmadi")
        private String address;

        @NotBlank(message = "XiCHOni ro'yxatdan chiqarish sababi kiritilmadi")
        private String reason;

        @NotBlank(message = "Ariza bayoni kiritilmadi!")
        private String description;

        @NotBlank(message = "Asos hujjati jo'natilmadi")
        private String actPath;

        @Override
        public AppealType getAppealType() {
                return AppealType.DEREGISTER_HF;
        }

        @Override
        public LocalDate getDeadline() {
                return null;
        }
}
