package uz.technocorp.ecosystem.modules.accreditationappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccreditationAppealDto implements AppealDto {

    @SkipDb
    @Schema(hidden = true)
    private String phoneNumber;

    @SkipDb
    @Schema(hidden = true)
    private String address;

    @SkipDb
    @Schema(hidden = true)
    private Integer regionId;

    @SkipDb
    @Schema(hidden = true)
    private Integer districtId;

    @Enumerated(EnumType.STRING)
    private List<AccreditationSphere> accreditationSpheres;

    @NotBlank(message = "Mas'ul vakilning ism sharifi yuborilmadi!")
    private String responsiblePersonName;

    @Override
    public AppealType getAppealType() {
        return AppealType.ACCREDIT_EXPERT_ORGANIZATION;
    }

    @SkipDb
    private String accreditationFieldPath;

    @SkipDb
    private String organizationCharterPath;

    @SkipDb
    private String declarationConformityPath;

    private LocalDate declarationConformityExpiryDate;

    @SkipDb
    private String receiptPath;

    @SkipDb
    private String employeesInfoPath;

    @SkipDb
    private String accreditationResourcedPath;

    @SkipDb
    private String propertyOwnerShipPath;
    @SkipDb
    private String qualityPerformanceInstructionPath;
    @SkipDb
    private String qualityManagementSystemPath;

    @Schema(hidden = true)
    private Map<String, FileDto> files = new HashMap<>();

    public void buildFiles() {
        files.put("accreditationFieldPath", new FileDto(accreditationFieldPath, null));
        files.put("organizationCharterPath", new FileDto(organizationCharterPath, null));
        files.put("declarationConformityPath", new FileDto(declarationConformityPath, declarationConformityExpiryDate));
        files.put("receiptPath", new FileDto(receiptPath, null));
        files.put("employeesInfoPath", new FileDto(employeesInfoPath, null));
        files.put("accreditationResourcedPath", new FileDto(accreditationResourcedPath, null));
        files.put("propertyOwnerShipPath", new FileDto(propertyOwnerShipPath, null));
        files.put("qualityPerformanceInstructionPath", new FileDto(qualityPerformanceInstructionPath, null));
        files.put("qualityManagementSystemPath", new FileDto(qualityManagementSystemPath, null));
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
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
