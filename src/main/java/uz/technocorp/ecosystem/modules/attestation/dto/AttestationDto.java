package uz.technocorp.ecosystem.modules.attestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeDto;

import java.time.LocalDate;
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

    @NotBlank(message = "Telefon raqam kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "XICHO tanlanmadi")
    private UUID hfId;

    @NotNull
    @Min(value = 1)
    @Max(value = 2)
    private Integer direction;

    @NotEmpty
    private List<@Valid EmployeeDto> pinList;

    @Override
    public AppealType getAppealType() {
        return AppealType.ATTESTATION;
    }

    // Other fields
    @Schema(hidden = true)
    private Integer regionId;

    @Schema(hidden = true)
    private Integer districtId;

    @Schema(hidden = true)
    private String address;

    @Schema(hidden = true)
    private Map<String, String> files;

    @Schema(hidden = true)
    private LocalDate deadline;

    @Schema(hidden = true)
    private AttestationCreateDto createDto;

    /**
     * Tashkilot kesimida (pagination) :
     *
     * 1. Tashkilot manzili
     * 2. Tashkilot STIR
     * 3. Xicho nomi
     * 4. Xicho addressi
     * 5. Jami xodimlar soni
     * 6. Attestatsiyadan o'tgan raxbar xodimlar soni
     * 7. Attestatsiyadan o'tgan texnik xodimlar soni
     * 8. Attestatsiyadan o'tgan xodimlar soni
     *
     *
     * Xicho kesimida (pagination) :
     *
     * 1. Xodim FISH
     * 2. Lavozimi
     * 3. PINFL
     * 4. Tashkilot nomi
     * 5. Tashkilot STIR
     * 6. Xicho nomi
     * 7. Attestatsiya sanasi
     */
}
