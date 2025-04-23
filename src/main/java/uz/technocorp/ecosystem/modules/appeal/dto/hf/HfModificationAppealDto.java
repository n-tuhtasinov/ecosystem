package uz.technocorp.ecosystem.modules.appeal.dto.hf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.hazardousfacility.enums.HFSphere;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 * @description XICHO ma'lumotlrini o'zgartirganda ariza polyalarini XICHO reestrdan ma'lumoalrni olib kelib to'ldirib, frontdan to'liq HfAppealDTO ni jo'natilishi kerak
 */
@Getter
@Setter
@NoArgsConstructor
public class HfModificationAppealDto extends HfAppealDto {

        @NotNull(message = "Hisobga olish raqami kiritilmadi!")
        private UUID hazardousFacilityId;

        @NotBlank(message = "Xichoga o'zgartirish kiritish sababi kiritilmadi!")
        private String reason;

        @NotBlank(message = "Asos fayli biriktirilmadi!")
        private String actPath;

        @Override
        public AppealType getAppealType() {
                return AppealType.MODIFY_HF;
        }

        public HfModificationAppealDto(String phoneNumber, String email, String upperOrganization, String name, String address, Integer hazardousFacilityTypeId, String extraArea, Integer regionId, Integer districtId, String location, String hazardousSubstance, List<HFSphere> spheres, String identificationCardPath, String receiptPath, String expertOpinionPath, String projectDocumentationPath, String cadastralPassportPath, String industrialSafetyDeclarationPath, String insurancePolicyPath, String licensePath, String permitPath, String certificationPath, String deviceTestingPath, String appointmentOrderPath, String ecologicalConclusionPath, String replyLetterPath, UUID hazardousFacilityId, String reason, String actPath) {
                super(phoneNumber, email, upperOrganization, name, address, hazardousFacilityTypeId, extraArea, regionId, districtId, location, hazardousSubstance, spheres, identificationCardPath, receiptPath, expertOpinionPath, projectDocumentationPath, cadastralPassportPath, industrialSafetyDeclarationPath, insurancePolicyPath, licensePath, permitPath, certificationPath, deviceTestingPath, appointmentOrderPath, ecologicalConclusionPath, replyLetterPath);
                this.hazardousFacilityId = hazardousFacilityId;
                this.reason = reason;
                this.actPath = actPath;
        }
}
