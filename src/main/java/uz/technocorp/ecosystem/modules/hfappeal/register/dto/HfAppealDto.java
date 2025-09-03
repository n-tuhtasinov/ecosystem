package uz.technocorp.ecosystem.modules.hfappeal.register.dto;


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
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HfAppealDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon raqami kiritilmadi!")
    private String phoneNumber;

    private String upperOrganization;

    @NotBlank(message = "Xicho nomi kiritilmadi")
    private String name;

    @SkipDb
    @NotBlank(message = "Manzil kiritilmadi!")
    private String address;

    @NotNull(message = "XICHO turi tanlanmadi!")
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

    @NotBlank(message = "Xavfli moddalarning nomi va miqdori kiritilmadi!")
    private String hazardousSubstance;

    @Enumerated(EnumType.STRING)
    private List<HFSphere> spheres;

    @SkipDb
    @NotBlank(message = "Identifikatsiya varag'i fayli biriktirilmadi!")
    private String identificationCardPath;
    private LocalDate identificationCardExpiryDate;

    @SkipDb
    @NotBlank(message = "XICHOni ro'yxatga olish uchun to'lov kvitansiyasi fayli biriktirilmadi!")
    private String receiptPath;

    @SkipDb
    private String expertOpinionPath;
    private LocalDate expertOpinionExpiryDate;

    @SkipDb
    private String projectDocumentationPath;
    private LocalDate projectDocumentationExpiryDate;

    @SkipDb
    private String cadastralPassportPath;
    private LocalDate cadastralPassportExpiryDate;

    @SkipDb
    private String industrialSafetyDeclarationPath;
    private LocalDate industrialSafetyDeclarationExpiryDate;

    @SkipDb
    private String insurancePolicyPath;
    private LocalDate insurancePolicyExpiryDate;

    @SkipDb
    private String licensePath;
    private LocalDate licenseExpiryDate;

    @SkipDb
    private String permitPath;
    private LocalDate permitExpiryDate;

    @SkipDb
    private String certificationPath;
    private LocalDate certificationExpiryDate;

    @SkipDb
    private String deviceTestingPath;
    private LocalDate deviceTestingExpiryDate;

    @SkipDb
    private String appointmentOrderPath;
    private LocalDate appointmentOrderExpiryDate;

    @SkipDb
    private String ecologicalConclusionPath;
    private LocalDate ecologicalConclusionExpiryDate;

    @SkipDb
    private String fireSafetyConclusionPath;
    private LocalDate fireSafetyConclusionExpiryDate;

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
        return RegistrationMode.OFFICIAL;
    }

    public void buildFiles() {
        files.put("identificationCardPath", new FileDto(identificationCardPath, identificationCardExpiryDate));
        files.put("receiptPath", new FileDto(receiptPath, null));
        files.put("expertOpinionPath", new FileDto(expertOpinionPath, expertOpinionExpiryDate));
        files.put("projectDocumentationPath", new FileDto(projectDocumentationPath, projectDocumentationExpiryDate));
        files.put("cadastralPassportPath", new FileDto(cadastralPassportPath, cadastralPassportExpiryDate));
        files.put("industrialSafetyDeclarationPath", new FileDto(industrialSafetyDeclarationPath, industrialSafetyDeclarationExpiryDate));
        files.put("insurancePolicyPath", new FileDto(insurancePolicyPath, insurancePolicyExpiryDate));
        files.put("licensePath", new FileDto(licensePath, licenseExpiryDate));
        files.put("permitPath", new FileDto(permitPath, permitExpiryDate));
        files.put("certificationPath", new FileDto(certificationPath, certificationExpiryDate));
        files.put("deviceTestingPath", new FileDto(deviceTestingPath, deviceTestingExpiryDate));
        files.put("appointmentOrderPath", new FileDto(appointmentOrderPath, appointmentOrderExpiryDate));
        files.put("ecologicalConclusionPath", new FileDto(ecologicalConclusionPath, ecologicalConclusionExpiryDate));
        files.put("fireSafetyConclusionPath", new FileDto(fireSafetyConclusionPath, fireSafetyConclusionExpiryDate));
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

}
