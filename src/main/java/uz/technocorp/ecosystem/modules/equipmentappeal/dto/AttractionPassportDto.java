package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.06.2025
 * @since v1.0
 */
public class AttractionPassportDto {

    @NotBlank(message = "Attraksion nomi kiritilmadi")
    private String attractionName;

    @NotNull(message = "Attraksion turi tanlanmadi")
    private Integer childEquipmentId;

    private String childEquipmentName;

    @NotNull(message = "Attraksion tipi tanlanmadi")
    private Integer childSortId;

    private String childSortName;

    @NotNull(message = "Ishlab chiqarilgan sana kiritilmadi")
    private LocalDate manufacturedAt;

    @NotNull(message = "Dastlabki foydalanishga qabul qilingan sana kiritilmadi")
    private LocalDate acceptedAt;

    @NotNull(message = "Hizmat muddati kiritilmadi")
    private Integer servicePeriod;

    @NotBlank(message = "Zavod raqami kirtilmadi")
    private String factoryNumber;

    @NotBlank(message = "Ishlab chiqarilgan mamlakat kirtilmadi")
    private String country;

    @NotNull(message = "Atraksion joylashgan viloyat tanlanmadi")
    private Integer regionId;

    @NotNull(message = "Atraksion joylashgan tuman tanlanmadi")
    private Integer districtId;

    @NotBlank(message = "Atraksion joylashgan manzil kiritilmadi")
    private String address;

    @NotBlank(message = "Atraksion joylashgan geolokatsiya kiritilmadi")
    private String location;

    @NotNull(message = "Biomexanik havf darajasi tanlanmadi")
    private RiskLevel riskLevel;

    @NotBlank(message = "birka fayli yuklanmadi")
    private String labelPath;

    @NotBlank(message = "Pasport fayli yuklanmadi")
    private String passportPath;

    @NotBlank(message = "Sertifikat fayli yuklanmadi")
    private String equipmentCertPath;

    @NotBlank(message = "Masul shaxs tayinlanganlik to'g'risida buyruq fayli yuklanmadi")
    private String assignmentDecreePath;

    @NotBlank(message = "Texnik tayyorligi to'g'risida dalolatnoma yuklanmadi")
    private String techReadinessActPath;

    @NotBlank(message = "Mavsumga tayyorligi to'g'risida dalolatnoma yuklanmadi")
    private String seasonalReadinessActPath;

    @NotBlank(message = "Soz holatda va undan xavsiz foydalanish bo'yicha mas'ul shaxs buyrug'i")
    private String safetyDecreePath;

    @NotBlank(message = "Foydalanishga qabul qilish guvohnomasi yuklanmadi")
    private String acceptanceCertPath;



























}
