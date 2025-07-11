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
 * @created 02.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpConclusionsView {

    private UUID id;
    private String customerLegalAddress;
    private String customerLegalName;
    private String customerFullName;
    private String customerPhoneNumber;
    private Long customerTin;
    private List<AccreditationSphere> spheres;
    private String expertOrganizationName;
    private LocalDate expertiseConclusionDate;
    private String expertiseConclusionNumber;
    private String expertiseObjectName;

    private String customerLegalForm;
    private String expertiseConclusionPath;
    private String firstSymbolsGroup;
    private String secondSymbolsGroup;
    private String thirdSymbolsGroup;
    private String objectAddress;
}
