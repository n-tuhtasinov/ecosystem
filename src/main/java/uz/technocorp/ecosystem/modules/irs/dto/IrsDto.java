package uz.technocorp.ecosystem.modules.irs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.03.2025
 * @since v1.0
 */
public record IrsDto(

        String parentOrganization,

        @NotBlank(message = "Mas'ul shaxs FIOsi kiritilmadi")
        String supervisorName,

        @NotBlank(message = "Mas'ul shaxsning lovozimi kiritilmadi")
        String supervisorPosition,

        @NotBlank(message = "Mas'ul shaxsning statusi kiritilmadi")
        String supervisorStatus,

        @NotBlank(message = "Mas'ul shaxs ma'lumoti kiritilmadi")
        String supervisorEducation,

        @NotBlank(message = "Mas'ul shaxsning telfon raqami kiritilmadi")
        String supervisorPhoneNumber,

        @NotBlank(message = "Tashkilot bo'linmasi nomi kiritilmadi")
        String division,

        @NotBlank(message = "Identifikatsiya raqami kiritilmadi")
        String identifierType,

        @NotBlank(message = "Radionuklit belgisi kiritilmadi")
        String symbol,

        @NotBlank(message = "Foydalaniladigan soha kiritilmadi")
        String sphere,

        @NotBlank(message = "Zavod raqami kiritilmadi")
        String factoryNumber,

        @NotBlank(message = "Seriya raqami kiritilmadi")
        String serialNumber,

        @NotBlank(message = "Ativligi kiritilmadi")
        Integer activity,

        @NotBlank(message = "Tipi kiritilmadi")
        String type,

        @NotBlank(message = "kategoriyasi kiritilmadi")
        String category,

        @NotBlank(message = "Ishlab chiqarilgan davlat nomi kiritilmadi")
        String country,

        @NotBlank(message = "Ishlab chiqarilgan sana kiritilmadi")
        String manufacturedAt,

        @NotBlank(message = "Qabul qilib olingan tashkilot nomi kiritilmadi")
        String acceptedFrom,

        @NotBlank(message = "Qabul qilib olingan sana kiritilmadi")
        String acceptedAt,

        @NotNull(message = "Holati tanlanmadi")
        Boolean isValid,

        @NotBlank(message = "Foydalanish maqsadi tanlanmadi")
        String usageType,

        @NotBlank(message = "Jaqlanayotgan joy kiritilmadi")
        String storageLocation,

        @NotBlank(message = "INM passporti yuklangan path kiritilmadi")
        String passportPath,

        String additionalFilePath,

        String description,

        @NotNull(message = "INM joylashgan viloyat tanlanmadi")
        Integer regionId,

        @NotNull(message = "INM joylashgan tuman tanlanmadi")
        Integer districtId,

        @NotBlank(message = "INM joylashgan manzil kiritilmadi")
        String address

) {
}
