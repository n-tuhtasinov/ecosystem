package uz.technocorp.ecosystem.modules.declarationappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeclarationDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon raqam jo'natilmadi")
    private String phoneNumber;

    @NotNull(message = "XICHO idsi jo'natilmadi")
    private UUID hfId;

    @NotBlank(message = "XICHO nomi jo'natilmadi")
    private String hfName;

    @NotBlank(message = "XICHO ro'yhatga olingan raqami ko'natilmadi")
    private String hfRegistryNumber;

    @SkipDb
    @NotBlank(message = "XICHO manzili jo'natilmadi")
    private String address;

    @NotNull(message = "Viloyat IDsi jo'natilmadi")
    private Integer regionId;

    @NotNull(message = "Tuman IDsi jo'natilmadi")
    private Integer districtId;

    @NotBlank(message = "Deklaratsiya qilingan obyekt haqida ma'lumot kiritilmadi")
    private String information;

    @NotNull(message = "Deklaratsiya ishlab chiqqan tashkilot STIRi jo'natilmadi")
    private Long producingOrganizationTin;

    @NotBlank(message = "Deklaratsiya ishlab chiqqan tashkilot nomi jo'natilmadi")
    private String producingOrganizationName;

    @NotBlank(message = "Deklaratsiya qilingan obyektni ishlatayotgan tashkilot nomi jo'natilmadi")
    private String operatingOrganizationName;

    @NotBlank(message = "Expertiza raqami kiritilmadi")
    private String expertiseNumber;

    @NotNull(message = "Expertiza sanasi kiritilmadi")
    private LocalDate expertiseDate;

    private String registrationOrganizationName;

    @SkipDb
    @NotBlank(message = "Deklaratsiya titul varag'i nusxasi jo'natilmadi")
    private String declarationPath;

    @SkipDb
    @NotBlank(message = "Deklaratsiya kelishganlik titul varag'i nusxasi jo'natilmadi")
    private String agreementPath;

    @Schema(hidden = true)
    private Map<String, FileDto> files = new HashMap<>();

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_DECLARATION;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }

    public void buildFiles(){
        files.put("declarationPath", new FileDto(declarationPath, null));
        files.put("agreementPath", new FileDto(agreementPath, null));
    }

    @Schema(hidden = true)
    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }
}
