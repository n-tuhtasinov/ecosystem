package uz.technocorp.ecosystem.modules.appeal.dto.hf;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.hazardousfacility.enums.HFSphere;

import java.time.LocalDate;
import java.util.List;

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

    private String email;
    private String upperOrganization;
    private String name;
    @NotBlank(message = "Manzil kiritilmadi!")
    private String address;
    @NotNull(message = "XICHO turi tanlanmadi!")
    private Integer hazardousFacilityTypeId;
    private String extraArea;
    private String description;
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

    @NotBlank(message = "Identifikatsiya varag'i fayli biriktirilmadi!")
    private String identificationCardPath;

    @NotBlank(message = "XICHOni ro'yxatga olish uchun to'lov kvitansiyasi fayli biriktirilmadi!")
    private String receiptPath;

    private String expertOpinionPath;

    private String projectDocumentationPath;

    private String cadastralPassportPath;

    private String industrialSafetyDeclarationPath;

    private String insurancePolicyPath;

    private String licensePath;

    private String permitPath;

    private String certificationPath;

    private String deviceTestingPath;

    private String appointmentOrderPath;

    private String ecologicalConclusionPath;

    private String replyLetterPath;

    @NotBlank(message = "Ariza fayli joylashgan path jo'natilmadi")
    private String appealPath;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HF;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
