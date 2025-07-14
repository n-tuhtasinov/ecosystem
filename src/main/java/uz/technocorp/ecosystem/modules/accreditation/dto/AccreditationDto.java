package uz.technocorp.ecosystem.modules.accreditation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccreditationDto {

    @NotNull(message = "Ariza yuborilmadi!")
    private UUID appealId;

    @NotEmpty(message = "Akkridatsiya sohasi tanlanmadi")
    private List<AccreditationSphere> accreditationSpheres;

    @NotBlank(message = "Akkreditatsiya attestati raqami yuborilmadi!")
    private String certificateNumber;

    @NotNull(message = "Akkreditatsiya attestati amal qilish muddati yuborilmadi!")
    private LocalDate certificateValidityDate;

    @NotNull(message = "Akkreditatsiya attestati sanasi yuborilmadi!")
    private LocalDate certificateDate;

    @NotBlank(message = "Baholash komissiyasi qarori fayli yuklanmadi!")
    private String assessmentCommissionDecisionPath;

    @NotNull(message = "Baholash komissiyasi qarori sanasi yuborilmadi!")
    private LocalDate assessmentCommissionDecisionDate;

    @NotBlank(message = "Baholash komissiyasi qarori raqami yuborilmadi!")
    private String assessmentCommissionDecisionNumber;

    @NotBlank(message = "Akkreditatsiya komissiyasi qarori fayli yuklanmadi!")
    private String accreditationCommissionDecisionPath;

    @NotNull(message = "Akkreditatsiya komissiyasi qarori sanasi yuborilmadi!")
    private LocalDate accreditationCommissionDecisionDate;

    @NotBlank(message = "Akkreditatsiya komissiyasi qarori raqami yuborilmadi!")
    private String accreditationCommissionDecisionNumber;

    @NotBlank(message = "Ma'lumotnoma fayli yuklanmadi!")
    private String referencePath;

    @NotBlank(message = "Akkreditatsiya attestati ilova fayli yuklanmadi!")
    private String accreditationAttestationPath;
}
