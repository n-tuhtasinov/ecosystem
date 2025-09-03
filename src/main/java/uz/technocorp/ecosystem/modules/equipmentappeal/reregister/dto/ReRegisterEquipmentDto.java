package uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto;

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
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 06.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReRegisterEquipmentDto implements AppealDto {

    @NotBlank(message = "Telefon raqam kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "Qurilma turi tanlanmadi")
    private EquipmentType type;

    private UUID hfId;

    @NotBlank(message = "Qurilmaning eski ro'yxatga olish raqami kiritilmadi")
    private String oldRegistryNumber;

    @NotNull(message = "Viloyat tanlanmadi")
    private Integer regionId;

    @NotNull(message = "Tuman tanlanmadi")
    private Integer districtId;

    @NotBlank(message = "Qurilma manzili kiritilmadi")
    private String address;

    @NotBlank(message = "Qurilma joylashgan giolokatsiya jo'natilmadi")
    private String location;

    @NotNull(message = "Qisman texnik/tashqi va ichki ko'rik sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate partialCheckDate;

    @NotNull(message = "To'liq texnik ko'rik/gidrosinov/keyingi tekshirish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fullCheckDate;

    @SkipDb
    @NotBlank(message = "Qurilmaning birkasi bilan surati fayli kiritilmadi")
    private String labelPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning oldi-sotdi shartnomasi fayli kiritilmadi")
    private String saleContractPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning mas'ul shaxs tayinlanganligi to'g'risidagi buyruq fayli kiritilmadi")
    private String assignmentDecreePath;

    @SkipDb
    @NotBlank(message = "Qurilmaning montaj guvohnomasi fayli fayli kiritilmadi")
    private String installationCertPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning sertifikati fayli fayli kiritilmadi")
    private String equipmentCertPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning expertiza loyihasi fayli kiritilmadi")
    private String expertisePath;


    @Override
    @Schema(hidden = true)
    public AppealType getAppealType() {
        return AppealType.findByTypeAndSort(this.type.name(), "reRegisterEquipment");
    }

    @Override
    @Schema(hidden = true)
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }

    private Map<String, FileDto> files = new HashMap<>();

    private void buildFiles() {
        files.put("labelPath", new FileDto(labelPath, null));
        files.put("saleContractPath", new FileDto(saleContractPath, null));
        files.put("assignmentDecreePath", new FileDto(assignmentDecreePath, null));
        files.put("installationCertPath", new FileDto(installationCertPath, null));
        files.put("equipmentCertPath", new FileDto(equipmentCertPath, null));
        files.put("expertisePath", new FileDto(expertisePath, null));
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

    // Other fields
    @Schema(hidden = true)
    private String factoryNumber;
    @Schema(hidden = true)
    private String model;
    @Schema(hidden = true)
    private LocalDate manufacturedAt;
    @Schema(hidden = true)
    private String childEquipmentName;
    @Schema(hidden = true)
    private String factory;
    @Schema(hidden = true)
    private UUID hazardousFacilityId;
}
