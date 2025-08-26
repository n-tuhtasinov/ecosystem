package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttractionPassportDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon nomer jo'natilmadi")
    private String phoneNumber;

    @NotBlank(message = "Attraksion nomi kiritilmadi")
    private String attractionName;

    @NotNull(message = "Attraksion turi tanlanmadi")
    private Integer childEquipmentId;

    @Schema(hidden = true)
    private String childEquipmentName;

    @NotNull(message = "Attraksion tipi tanlanmadi")
    private Integer childEquipmentSortId;

    @Schema(hidden = true)
    private String childEquipmentSortName;

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

    @SkipDb
    @NotNull(message = "Atraksion joylashgan viloyat tanlanmadi")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Atraksion joylashgan tuman tanlanmadi")
    private Integer districtId;

    @SkipDb
    @NotBlank(message = "Atraksion joylashgan manzil kiritilmadi")
    private String address;

    @NotBlank(message = "Atraksion joylashgan geolokatsiya kiritilmadi")
    private String location;

    @NotNull(message = "Biomexanik havf darajasi tanlanmadi")
    private RiskLevel riskLevel;

    @SkipDb
    @NotBlank(message = "birka fayli yuklanmadi")
    private String labelPath;

    @SkipDb
    @NotBlank(message = "Pasport fayli yuklanmadi")
    private String passportPath;

    @SkipDb
    @NotBlank(message = "Sertifikat fayli yuklanmadi")
    private String equipmentCertPath;

    @SkipDb
    @NotBlank(message = "Masul shaxs tayinlanganlik to'g'risida buyruq fayli yuklanmadi")
    private String assignmentDecreePath;

    @SkipDb
    @NotBlank(message = "Texnik tayyorligi to'g'risida dalolatnoma yuklanmadi")
    private String techReadinessActPath;

    @SkipDb
    @NotBlank(message = "Mavsumga tayyorligi to'g'risida dalolatnoma yuklanmadi")
    private String seasonalReadinessActPath;

    @SkipDb
    @NotBlank(message = "Soz holatda va undan xavsiz foydalanish bo'yicha mas'ul shaxs buyrug'i")
    private String safetyDecreePath;

    @SkipDb
    @NotBlank(message = "Foydalanishga qabul qilish guvohnomasi yuklanmadi")
    private String acceptanceCertPath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.ATTRACTION_PASSPORT;

    public void buildFiles() {
        files.put("labelPath", labelPath);
        files.put("passportPath", passportPath);
        files.put("equipmentCertPath", equipmentCertPath);
        files.put("assignmentDecreePath", assignmentDecreePath);
        files.put("techReadinessActPath", techReadinessActPath);
        files.put("seasonalReadinessActPath", seasonalReadinessActPath);
        files.put("safetyDecreePath", safetyDecreePath);
        files.put("acceptanceCertPath", acceptanceCertPath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }


    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ATTRACTION_PASSPORT;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }
}
