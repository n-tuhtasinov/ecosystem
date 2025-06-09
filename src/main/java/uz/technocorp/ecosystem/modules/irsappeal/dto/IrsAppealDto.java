package uz.technocorp.ecosystem.modules.irsappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.03.2025
 * @since v1.0
 * @description IRS - ionizing radiation source (INM)
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrsAppealDto implements AppealDto {

        @NotBlank(message = "Tashkilot bilan bog'lanish uchun telefon raqam kiritilmadi")
        private String phoneNumber;

        private String parentOrganization;

        @NotBlank(message = "Mas'ul shaxs FIOsi kiritilmadi")
        private String supervisorName;

        @NotBlank(message = "Mas'ul shaxsning lovozimi kiritilmadi")
        private String supervisorPosition;

        @NotBlank(message = "Mas'ul shaxsning statusi kiritilmadi")
        private String supervisorStatus;

        @NotBlank(message = "Mas'ul shaxs ma'lumoti kiritilmadi")
        private String supervisorEducation;

        @NotBlank(message = "Mas'ul shaxsning telfon raqami kiritilmadi")
        private String supervisorPhoneNumber;

        @NotBlank(message = "Tashkilot bo'linmasi nomi kiritilmadi")
        private String division;

        @NotBlank(message = "Identifikatsiya raqami kiritilmadi")
        private String identifierType;

        @NotBlank(message = "Radionuklit belgisi kiritilmadi")
        private String symbol;

        @NotBlank(message = "Foydalaniladigan soha kiritilmadi")
        private String sphere;

        @NotBlank(message = "Zavod raqami kiritilmadi")
        private String factoryNumber;

        @NotBlank(message = "Seriya raqami kiritilmadi")
        private String serialNumber;

        @NotNull(message = "Ativligi kiritilmadi")
        private Integer activity;

        @NotBlank(message = "Tipi kiritilmadi")
        private String type;

        @NotBlank(message = "kategoriyasi kiritilmadi")
        private String category;

        @NotBlank(message = "Ishlab chiqarilgan davlat nomi kiritilmadi")
        private String country;

        @NotBlank(message = "Ishlab chiqarilgan sana kiritilmadi")
        private String manufacturedAt;

        @NotBlank(message = "Qabul qilib olingan tashkilot nomi kiritilmadi")
        private String acceptedFrom;

        @NotBlank(message = "Qabul qilib olingan sana kiritilmadi")
        private String acceptedAt;

        @NotNull(message = "Holati tanlanmadi")
        private Boolean isValid;

        @NotBlank(message = "Foydalanish maqsadi tanlanmadi")
        private String usageType;

        @NotBlank(message = "Jaqlanayotgan joy kiritilmadi")
        private String storageLocation;

        @NotBlank(message = "INM passporti yuklangan path kiritilmadi")
        private String passportPath;

        private String additionalFilePath;

        @NotNull(message = "INM joylashgan viloyat tanlanmadi")
        private Integer regionId;

        @NotNull(message = "INM joylashgan tuman tanlanmadi")
        private Integer districtId;

        @NotBlank(message = "INM joylashgan manzil kiritilmadi")
        private String address;

        @Schema(hidden = true)
        private Map<String, String> files = new HashMap<>();

        @Override
        public AppealType getAppealType() {
                return AppealType.REGISTER_IRS;
        }

        @Override
        public LocalDate getDeadline() {
                return LocalDate.now().plusDays(15);
        }

        public void buildFiles() {
                files.put("passportPath", passportPath);
                files.put("additionalFilePath", additionalFilePath);
        }

        @AssertTrue
        public boolean isFilesBuilt() {
                buildFiles();
                return true;
        }



}
