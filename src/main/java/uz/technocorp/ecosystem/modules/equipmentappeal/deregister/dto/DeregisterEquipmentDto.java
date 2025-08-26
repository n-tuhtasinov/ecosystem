package uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto;

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
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 04.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeregisterEquipmentDto implements AppealDto {

    @NotBlank(message = "Telefon raqam yuborilmadi")
    private String phoneNumber;

    @NotNull(message = "Ariza turi tanlanmadi")
    private EquipmentType type;

    @NotBlank(message = "Qurilmaning ro'yhatga olish raqami jo'natilmadi")
    private String registryNumber;

    private String description;

    @SkipDb
    @NotBlank(message = "Qurilmaning oldi sotdi shartnomasi kiritilmadi")
    private String purchaseAgreementPath;

    @SkipDb
    private String orderSuspensionPath;

    @SkipDb
    private String laboratoryReportPath;

    @SkipDb
    private String additionalInfoPath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    private void buildFiles() {
        files.put("purchaseAgreementPath", purchaseAgreementPath);
        files.put("orderSuspensionPath", orderSuspensionPath);
        files.put("laboratoryReportPath", laboratoryReportPath);
        files.put("additionalInfoPath", additionalInfoPath);
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

    @Override
    public AppealType getAppealType() {
        return AppealType.findByTypeAndSort(this.type.name(), "deregisterEquipment");
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }

    @SkipDb
    @Schema(hidden = true)
    private Integer regionId;

    @SkipDb
    @Schema(hidden = true)
    private Integer districtId;

    @SkipDb
    @Schema(hidden = true)
    private String address;

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
