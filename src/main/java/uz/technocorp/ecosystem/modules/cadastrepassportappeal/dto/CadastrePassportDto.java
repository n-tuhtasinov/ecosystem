package uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CadastrePassportDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon raqami kiritilmadi!")
    private String phoneNumber;

    @NotNull(message = "XICHO idsi jo'natilmadi")
    private UUID hfId;

    @NotBlank(message = "XICHO nomi jo'natilmadi")
    private String hfName;

    @SkipDb
    @NotBlank(message = "XICHO manzili jo'natilmadi")
    private String address;

    @NotNull(message = "TXYuZ kadast pasportini ishlab chiqargan tashkilot STIRi jo'natilmadi")
    private Long organizationTin;

    @NotBlank(message = "TXYuZ kadast pasportini ishlab chiqargan tashkilot nomi jo'natilmadi")
    private String organizationName;

    @NotBlank(message = "TXYuZ kadast pasportini ishlab chiqargan tashkilot manzili")
    private String organizationAddress;

    @NotBlank(message = "TXYuZ lokatsiyasi kiritilmadi")
    private String location;

    @SkipDb
    @NotBlank(message = "Passport titul varag'i nusxasi yuklanmadi")
    private String passportPath;

    @SkipDb
    @NotBlank(message = "Passportni kelishilganligi titul varag'i yuklanmadi")
    private String agreementPath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CADASTRE_PASSPORT;
    }

    @SkipDb
    @NotNull(message = "Xicho joylashgan viloyat jo'natilmadi")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Xicho joylashgan tuman jo'natilmadi")
    private Integer districtId;

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildFiles(){
        files.put("passportPath", passportPath);
        files.put("agreementPath", agreementPath);
    }

    @Schema(hidden = true)
    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }
}
