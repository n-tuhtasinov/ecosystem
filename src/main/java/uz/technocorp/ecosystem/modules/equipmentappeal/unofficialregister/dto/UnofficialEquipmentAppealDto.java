package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.04.2025
 * @since v1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UnofficialEquipmentAppealDto implements AppealDto {

    @SkipDb
    @NotNull(message = "INN yoki JSHIR yuborilmadi")
    private Long identity;

    @SkipDb
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @SkipDb
    private String phoneNumber;

    private UUID hazardousFacilityId;

    @Schema(hidden = true)
    private String hazardousFacilityName;

    @NotNull(message = "Qurilma turi tanlanmadi")
    private Integer childEquipmentId;

    @Schema(hidden = true)
    private String childEquipmentName;

    private String factoryNumber;

    @SkipDb
    @NotNull(message = "Qurilma joylashgan viloyat tanlanmadi")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Qurilma joylashgan tuman tanlanmadi")
    private Integer districtId;

    @SkipDb
    @NotBlank(message = "Qurilma joylashgan manzil jo'natilmadi")
    private String address;

    private String model;

    private String factory;

    @NotBlank(message = "Qurilma joylashgan giolokatsiya jo'natilmadi")
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturedAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate partialCheckDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fullCheckDate;

    @SkipDb
    private String labelPath;
    @SkipDb
    private LocalDate labelExpiryDate;

    @SkipDb
    private String saleContractPath;
    @SkipDb
    private LocalDate saleContractExpiryDate;

    @SkipDb
    private String equipmentCertPath;
    private LocalDate equipmentCertExpiryDate;

    @SkipDb
    private String assignmentDecreePath;
    private LocalDate assignmentDecreeExpiryDate;

    @SkipDb
    private String expertisePath;
    private LocalDate expertiseExpiryDate;

    @SkipDb
    private String installationCertPath;
    private LocalDate installationCertExpiryDate;

    @SkipDb
    private String additionalFilePath;
    private LocalDate additionalFileExpiryDate;

    private Map<String, FileDto> files = new HashMap<>();

    @Schema(hidden = true)
    private Map<String, String> parameters = new HashMap<>();

    @Schema(hidden = true)
    public abstract EquipmentType getType();

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.UNOFFICIAL;
    }

    private void buildFiles() {
        files.put("labelPath", new FileDto(labelPath, labelExpiryDate));
        files.put("saleContractPath", new FileDto(saleContractPath, saleContractExpiryDate));
        files.put("equipmentCertPath", new FileDto(equipmentCertPath, equipmentCertExpiryDate));
        files.put("assignmentDecreePath", new FileDto(assignmentDecreePath, assignmentDecreeExpiryDate));
        files.put("expertisePath", new FileDto(expertisePath, expertiseExpiryDate));
        files.put("installationCertPath", new FileDto(installationCertPath, installationCertExpiryDate));
        files.put("additionalFilePath", new FileDto(additionalFilePath, additionalFileExpiryDate));
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        this.fullCheckDate = partialCheckDate == null ? null : partialCheckDate.plusYears(1);  //to prevent invalid data coming from the frontend
        return true;
    }

    @AssertTrue(message = "STIR yoki JSHIR no'tog'ri yuborildi")
    public boolean isIdentityValid() {
        return identity.toString().length() == 9 || identity.toString().length() == 14;
    }

    @AssertTrue(message = "Tug'ilgan sana yuborilmadi")
    public boolean hasBirthDate() {
        return identity.toString().length() != 14 || (birthDate != null);
    }
}
