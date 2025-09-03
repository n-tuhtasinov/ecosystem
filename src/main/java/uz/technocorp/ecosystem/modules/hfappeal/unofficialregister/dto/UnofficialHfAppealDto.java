package uz.technocorp.ecosystem.modules.hfappeal.unofficialregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 25.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnofficialHfAppealDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon raqami kiritilmadi!")
    private String phoneNumber;

    @NotNull(message = "Tashkilot STIR kiritilmadi!")
    private Long legalTin;

    private String upperOrganization;

    @NotBlank(message = "Xicho nomi kiritilmadi")
    private String name;

    @SkipDb
    @NotBlank(message = "Manzil kiritilmadi!")
    private String address;

    private Integer hfTypeId;

    private String hfTypeName;

    private String extraArea;

    @SkipDb
    @NotNull(message = "Viloyat tanlanmadi!")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Tuman tanlanmadi!")
    private Integer districtId;

    @NotBlank(message = "Lokatsiya kiritilmadi!")
    private String location;

    private String hazardousSubstance;

    @Enumerated(EnumType.STRING)
    private List<HFSphere> spheres;

    @SkipDb
    private String identificationCardPath;

    @SkipDb
    private String receiptPath;

    @SkipDb
    private String expertOpinionPath;

    @SkipDb
    private String projectDocumentationPath;

    @SkipDb
    private String cadastralPassportPath;

    @SkipDb
    private String industrialSafetyDeclarationPath;

    @SkipDb
    private String insurancePolicyPath;

    @SkipDb
    private String licensePath;

    @SkipDb
    private String permitPath;

    @SkipDb
    private String certificationPath;

    @SkipDb
    private String deviceTestingPath;

    @SkipDb
    private String appointmentOrderPath;

    @SkipDb
    private String ecologicalConclusionPath;

    @SkipDb
    private String fireSafetyConclusionPath;

    @Schema(hidden = true)
    private Map<String, FileDto> files = new HashMap<>();

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HF;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.UNOFFICIAL;
    }

    public void buildFiles() {
        files.put("identificationCardPath", new FileDto(identificationCardPath, null));
        files.put("receiptPath", new FileDto(receiptPath, null));
        files.put("expertOpinionPath", new FileDto(expertOpinionPath, null));
        files.put("projectDocumentationPath", new FileDto(projectDocumentationPath, null));
        files.put("cadastralPassportPath", new FileDto(cadastralPassportPath, null));
        files.put("industrialSafetyDeclarationPath", new FileDto(industrialSafetyDeclarationPath, null));
        files.put("insurancePolicyPath", new FileDto(insurancePolicyPath, null));
        files.put("licensePath", new FileDto(licensePath, null));
        files.put("permitPath", new FileDto(permitPath, null));
        files.put("certificationPath", new FileDto(certificationPath, null));
        files.put("deviceTestingPath", new FileDto(deviceTestingPath, null));
        files.put("appointmentOrderPath", new FileDto(appointmentOrderPath, null));
        files.put("ecologicalConclusionPath", new FileDto(ecologicalConclusionPath, null));
        files.put("fireSafetyConclusionPath", new FileDto(fireSafetyConclusionPath, null));
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }
}
