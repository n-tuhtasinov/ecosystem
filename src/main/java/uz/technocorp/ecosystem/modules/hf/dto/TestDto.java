package uz.technocorp.ecosystem.modules.hf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.06.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto implements AppealDto {
    private String name;
    private String email;
    private String address;
    private List<String> spheres;
    private Integer hfTypeId;
    private String location;
    private Integer regionId;
    private String extraArea;
    private Integer districtId;
    private String hfTypeName;
    private String permitPath;
    private String licensePath;
    private String phoneNumber;
    private String receiptPath;
    private String replyLetterPath;
    private String certificationPath;
    private String deviceTestingPath;
    private String expertOpinionPath;
    private String upperOrganization;
    private String hazardousSubstance;
    private String insurancePolicyPath;
    private String appointmentOrderPath;
    private String cadastralPassportPath;
    private String identificationCardPath;
    private String ecologicalConclusionPath;
    private String projectDocumentationPath;
    private String industrialSafetyDeclarationPath;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HF;
    }

    @Override
    public LocalDate getDeadline() {
        return LocalDate.now();
    }
}
