package uz.technocorp.ecosystem.modules.hfappeal.deregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.shared.SkipDb;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.08.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HfDeregisterDto implements AppealDto {

    @NotBlank(message = "Telefon raqam kiritilmadi")
    private String phoneNumber;

    @NotBlank(message = "Hisobga olish raqami kiritilmadi")
    private String registryNumber;

    @NotBlank(message = "XICHO ni reestrdan chiqarish sabablari kiritilmadi")
    private String reasons;

    private String description;

    @SkipDb
    @NotBlank(message = "Asosli hujjat fayli kiritilmadi")
    private String justifiedDocumentPath;

    @Schema(hidden = true)
    private Map<String, FileDto> files = new HashMap<>();

    @SkipDb
    @Schema(hidden = true)
    private Integer regionId;

    @SkipDb
    @Schema(hidden = true)
    private Integer districtId;

    @SkipDb
    @Schema(hidden = true)
    private String address;

    @Override
    public AppealType getAppealType() {
        return AppealType.DEREGISTER_HF;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }

    private void buildFiles() {
        files.put("justifiedDocumentPath", new FileDto(justifiedDocumentPath, null));
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

    // Other fields
    @Schema(hidden = true)
    private String hfName;
}
