package uz.technocorp.ecosystem.modules.hf.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.04.2025
 * @since v1.0
 */
//TODO: fieldlarga validatsiya yozish kerak
public record HfDto(Long legalTin, Integer regionId, Integer districtId,
                    String phoneNumber, String upperOrganization, String name, String address,
                    String location, String hazardousSubstance,
                    Integer hfTypeId, String extraArea, String description, String registryNumber, LocalDate registrationDate,
                    String appointmentOrderPath, String cadastralPassportPath, String certificationPath, String permitPath,
                    String deviceTestingPath, String licensePath, String ecologicalConclusionPath, String expertOpinionPath,
                    String industrialSafetyDeclarationPath, String insurancePolicyPath, String projectDocumentationPath,
                    String identificationCardPath, String receiptPath,
                    @Enumerated(EnumType.STRING) List<HFSphere> spheres) {
}
