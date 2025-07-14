package uz.technocorp.ecosystem.modules.accreditation.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccreditationView {

    private UUID id;
    private Long tin;
    private String legalAddress;
    private String legalName;
    private String phoneNumber;
    private String fullName;
    private List<AccreditationSphere> accreditationSpheres;
    private String certificateNumber;
    private LocalDate certificateDate;
    private LocalDate certificateValidityDate;
    private String accreditationCommissionDecisionPath;
    private String assessmentCommissionDecisionPath;
    private String referencePath;
    private String accreditationAttestationPath;
}
