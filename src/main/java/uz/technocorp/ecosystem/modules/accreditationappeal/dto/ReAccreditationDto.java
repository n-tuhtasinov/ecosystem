package uz.technocorp.ecosystem.modules.accreditationappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
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
public class ReAccreditationDto implements AppealDto {

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

    @Override
    public AppealType getAppealType() {
        return AppealType.RE_ACCREDIT_EXPERT_ORGANIZATION;
    }

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @SkipDb
    private String accreditationFieldPath;

    @SkipDb
    private String organizationCharterPath;

    @SkipDb
    private String declarationConformityPath;

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

    @SkipDb
    private String certificateNumber;

    @SkipDb
    private LocalDate certificateDate;

    public void buildFiles() {
        files.put("accreditationFieldPath", accreditationFieldPath);
        files.put("organizationCharterPath", organizationCharterPath);
        files.put("declarationConformityPath", declarationConformityPath);
        files.put("receiptPath", receiptPath);
        files.put("employeesInfoPath", employeesInfoPath);
        files.put("accreditationResourcedPath", accreditationResourcedPath);
        files.put("propertyOwnerShipPath", propertyOwnerShipPath);
        files.put("qualityPerformanceInstructionPath", qualityPerformanceInstructionPath);
        files.put("qualityManagementSystemPath", qualityManagementSystemPath);
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
}
