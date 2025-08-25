package uz.technocorp.ecosystem.modules.attestationappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationDirection;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttestationDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon raqam kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "XICHO ID jo'natilmadi")
    private UUID hfId;

    @NotBlank(message = "XICHO tanlanmadi")
    private String hfRegistryNumber;

    private String hfUpperOrganizationName;

    @NotBlank(message = "Tashkilot nomi kiritilmadi")
    private String legalName;

    @NotNull(message = "STIR kiritilmadi")
    private Long legalTin;

    @NotBlank(message = "XICHO nomi kiritilmadi")
    private String hfName;

    @SkipDb
    @NotBlank(message = "XICHO manzili kiritilmadi")
    private String address;

    @SkipDb
    @NotNull(message = "XICHO joylashgan viloyat kiritilmadi")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "XICHO joylashgan tuman kiritilmadi")
    private Integer districtId;

    @NotNull(message = "Attestatsiyadan o'tadigan xodimlar turi tanlanmadi")
    private AttestationDirection direction;

    private LocalDateTime dateOfAttestation;

    @NotEmpty(message = "Attestatsiya uchun xodim tanlanmadi")
    private List<@Valid EmployeeDto> employeeList;

    // Other field
    @SkipDb
    @Schema(hidden = true)
    private String dynamicRows;

    // Override fields
    @Override
    @Schema(hidden = true)
    public AppealType getAppealType() {
        return AttestationDirection.REGIONAL.equals(direction) ? AppealType.ATTESTATION_REGIONAL : AppealType.ATTESTATION_COMMITTEE;
    }

    @SkipDb
    @Schema(hidden = true)
    private Map<String, String> files;

    @Override
    public LocalDate getDeadline() {
        return dateOfAttestation != null ? dateOfAttestation.toLocalDate() : null;
    }

    @Override
    public AppealMode getAppealMode() {
        return AppealMode.OFFICIAL;
    }

    // Validation
    @Schema(hidden = true)
    @AssertTrue(message = "Texnik va oddiy xodimlar attestatsiyadan o'tkazish sanasi kiritilishi kerak")
    public boolean isDateOfAttestation() {
        if (AttestationDirection.REGIONAL.equals(direction)) {
            return dateOfAttestation != null;
        }
        return true;
    }

    @Schema(hidden = true)
    @AssertTrue(message = "Kiritilgan xodim tanlangan yo'nalishda emas")
    public boolean isValidEmployeesLevel() {
        return employeeList.stream().allMatch(e -> e.getLevel().getDirection().equals(direction.getValue()));
    }
}
