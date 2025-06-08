package uz.technocorp.ecosystem.modules.hfappeal.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NotBlank(message = "Telefon raqami kiritilmadi!")
    private String phoneNumber;

    private String upperOrganization;

    @NotBlank(message = "Xicho nomi kiritilmadi")
    private String name;

    @NotBlank(message = "Manzil kiritilmadi!")
    private String address;

    @NotNull(message = "XICHO turi tanlanmadi!")
    private Integer hfTypeId;

    private String hfTypeName;

    private String extraArea;

    @NotNull(message = "Viloyat tanlanmadi!")
    private Integer regionId;

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

    @SkipDb
    @NotBlank(message = "XICHOni ro'yxatga olish uchun to'lov kvitansiyasi fayli biriktirilmadi!")
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

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HF;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildFiles() {
//        files.put("identificationCardPath", identificationCardPath);
//        files.put("receiptPath", receiptPath);
//        files.put("expertOpinionPath", expertOpinionPath);
//        files.put("projectDocumentationPath", projectDocumentationPath);
//        files.put("cadastralPassportPath", cadastralPassportPath);
//        files.put("industrialSafetyDeclarationPath", industrialSafetyDeclarationPath);
//        files.put("insurancePolicyPath", insurancePolicyPath);
//        files.put("licensePath", licensePath);
//        files.put("permitPath", permitPath);
//        files.put("certificationPath", certificationPath);
//        files.put("deviceTestingPath", deviceTestingPath);
//        files.put("appointmentOrderPath", appointmentOrderPath);
//        files.put("ecologicalConclusionPath", ecologicalConclusionPath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

}
