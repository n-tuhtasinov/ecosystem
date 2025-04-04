package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class HfModificationAppealDto implements AppealDto {

        @NotNull(message = "Hisobga olish raqami kiritilmadi!")
        UUID hazardousFacilityId;
        @NotBlank(message = "Telefon raqami kiritilmadi!")
        String phoneNumber;
        @NotBlank(message = "Pochta manzili kiritilmadi!")
        String email;
        @NotBlank(message = "Xichoga o'zgartirish kiritish yoki ro'yxatdan chiqarish sababi kiritilmadi!")
        String reason;
        @NotBlank(message = "Ariza bayoni kiritilmadi!")
        String statement;
        @NotBlank(message = "Dalolatnoma biriktirilmadi!")
        String actPath;
        @NotBlank(message = "Ariza fayli biriktirilmadi!")
        String appealPath;

        Integer regionId;

        @Override
        public AppealType getAppealType() {
                return AppealType.DEREGISTER_HF;
        }

        @Override
        public Integer getRegionId() {
                return null;
        }

        @Override
        public Integer getDistrictId() {
                return null;
        }

        @Override
        public String getAddress() {
                return null;
        }

        @Override
        public LocalDate getDeadline() {
                return null;
        }
}
