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
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;

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
    private String saleContractPath;

    @SkipDb
    private String equipmentCertPath;

    @SkipDb
    private String assignmentDecreePath;

    @SkipDb
    private String expertisePath;

    @SkipDb
    private String installationCertPath;

    @SkipDb
    private String additionalFilePath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Schema(hidden = true)
    private Map<String, String> parameters = new HashMap<>();

    @Schema(hidden = true)
    public abstract EquipmentType getType();

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.UNOFFICIAL;
    }


    public void buildFiles() {
        files.put("labelPath", labelPath);
        files.put("saleContractPath", saleContractPath);
        files.put("equipmentCertPath", equipmentCertPath);
        files.put("assignmentDecreePath", assignmentDecreePath);
        files.put("expertisePath", expertisePath);
        files.put("installationCertPath", installationCertPath);
        files.put("additionalFilePath", additionalFilePath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        this.fullCheckDate = partialCheckDate==null? null : partialCheckDate.plusYears(1);  //to prevent invalid data coming from the frontend
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
