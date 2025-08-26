package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

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
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnofficialAttractionDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon nomer jo'natilmadi")
    private String phoneNumber;

    @NotNull(message = "Atraksion pasporti IDsi jo'natilmadi")
    private UUID attractionPassportId;

    @Schema(hidden = true)
    private String attractionPassportRegistryNumber;

    private String parentOrganization;

    @Schema(hidden = true)
    private String attractionName;

    @Schema(hidden = true)
    private Integer childEquipmentId;

    @Schema(hidden = true)
    private String childEquipmentName;

    @Schema(hidden = true)
    private Integer childEquipmentSortId;

    @Schema(hidden = true)
    private String childEquipmentSortName;

    @Schema(hidden = true)
    private LocalDate manufacturedAt;

    @Schema(hidden = true)
    private LocalDate acceptedAt;

    @Schema(hidden = true)
    private String factoryNumber;

    @Schema(hidden = true)
    private String country;

    @NotBlank(message = "Atraksion joylashgan geolokatsiya kiritilmadi")
    private String location;

    @Schema(hidden = true)
    private RiskLevel riskLevel;

    @SkipDb
    @NotNull(message = "Qurilma joylashgan viloyat tanlanmadi")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Qurilma joylashgan tuman tanlanmadi")
    private Integer districtId;

    @SkipDb
    @NotBlank(message = "Qurilma joylashgan manzil jo'natilmadi")
    private String address;

    @SkipDb
    @NotBlank(message = "Qurilmaning birkasi bilan surati jo'natilmadi")
    private String labelPath;

    @SkipDb
    @NotBlank(message = "Atraksion passporti jo'natilmadi")
    private String passportPath;

    @SkipDb
    @NotBlank(message = "Texnik foydalanish qo'llanmasi jo'natilmadi")
    private String technicalManualPath;

    @SkipDb
    @NotBlank(message = "Xizmat ko'rsatish va ta'mirlash qo'llanmasi jo'natilmadi")
    private String serviceManualPath;

    @SkipDb
    @NotBlank(message = "Texnik jurnal nusxasi jo'natilmadi")
    private String technicalJournalPath;

    @SkipDb
    @NotBlank(message = "Qabul qilinganligi xujjatlari jo'natilmadi")
    private String acceptanceFilePath;

    @SkipDb
    @NotBlank(message = "Marshrut ma'lumotlari jo'natilmadi")
    private String routeInfoPath;

    @SkipDb
    @NotBlank(message = "Muvofiqlik sertifikati jo'natilmadi")
    private String conformityCertPath;

    @SkipDb
    @NotBlank(message = "Xavfsiz foydalanish xulosasi jo'natilmadi")
    private String safetyUsageReportPath;

    @SkipDb
    @NotBlank(message = "Sug'urta polisi jo'natilmadi")
    private String insurancePolicyPath;

    @SkipDb
    @NotBlank(message = "Beomexanik xavf hujjati jo'natilmadi")
    private String biomechanicalRiskFilePath;

    @SkipDb
    @NotBlank(message = "Texnik holat dalolatnomasi jo'natilmadi")
    private String technicalStatusActPath;

    @SkipDb
    @NotBlank(message = "Foydalanish huquqi jo'natilmadi")
    private String usageRightsPath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.ATTRACTION;

    public void buildFiles() {
        files.put("labelPath", labelPath);
        files.put("passportPath", passportPath);
        files.put("technicalManualPath", technicalManualPath);
        files.put("serviceManualPath", serviceManualPath);
        files.put("technicalJournalPath", technicalJournalPath);
        files.put("acceptanceFilePath", acceptanceFilePath);
        files.put("routeInfoPath", routeInfoPath);
        files.put("conformityCertPath", conformityCertPath);
        files.put("safetyUsageReportPath", safetyUsageReportPath);
        files.put("insurancePolicyPath", insurancePolicyPath);
        files.put("biomechanicalRiskFilePath", biomechanicalRiskFilePath);
        files.put("technicalStatusActPath", technicalStatusActPath);
        files.put("usageRightsPath", usageRightsPath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }


    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ATTRACTION;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.UNOFFICIAL;
    }
}
