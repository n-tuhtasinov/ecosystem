package uz.technocorp.ecosystem.modules.hf.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.04.2025
 * @since v1.0
 */
//TODO: fieldlarga validatsiya yozish kerak
public record HfDto(Long legalTin, String legalName, Integer regionId, Integer districtId, String legalAddress,
                    String phoneNumber, String email, String upperOrganization, String name, String address,
                    String location, String hazardousSubstance,
                    Integer hfTypeId, String extraArea, String description, String registryNumber,
                    String appointmentOrderPath, String cadastralPassportPath, String certificationPath, String permitPath,
                    String deviceTestingPath, String licensePath, String ecologicalConclusionPath, String expertOpinionPath,
                    String industrialSafetyDeclarationPath, String insurancePolicyPath, String projectDocumentationPath,
                    String replyLetterPath, String identificationCardPath, String receiptPath,
                    @Enumerated(EnumType.STRING) List<HFSphere> spheres) {
}
