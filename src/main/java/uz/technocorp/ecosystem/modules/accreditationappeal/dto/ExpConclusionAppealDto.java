package uz.technocorp.ecosystem.modules.accreditationappeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 03.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpConclusionAppealDto implements AppealDto {

    @SkipDb
    @NotBlank(message = "Telefon nomer yuborilmadi")
    private String phoneNumber;

    @NotNull(message = "Buyurtamachi tashkilot STIRi jo'natilmadi")
    private Long customerTin;

    @NotBlank(message = "Buyurtamachi tashkilot nomi jo'natilmadi")
    private String customerLegalName;

    @NotBlank(message = "Buyurtamachi tashkilot tashkiliy-huquqiy shakli jo'natilmadi")
    private String customerLegalForm;

    @NotBlank(message = "Buyurtamachi tashkilot manzili jo'natilmadi")
    private String customerLegalAddress;

    @NotBlank(message = "Buyurtamachi tashkilot telefon raqami jo'natilmadi")
    private String customerPhoneNumber;

    @NotBlank(message = "Buyurtamachi tashkilot rahbarining FIOsi jo'natilmadi")
    private String customerFullName;

    @NotNull(message = "Ekspertiza xulosasini hisobga olish uchun topshirgan sana yuborilmadi!")
    private LocalDate submissionDate;

    @NotNull(message = "Kuzatuv xati sanasi yuborilmadi!")
    private LocalDate monitoringLetterDate;

    @NotBlank(message = "Kuzatuv xati raqami yuborilmadi!")
    private String monitoringLetterNumber;

    @NotBlank(message = "Ekspertiza obyekti nomi yuborilmadi!")
    private String objectName;

    @NotBlank(message = "Birinchi belgilar guruhi yuborilmadi!")
    private String firstSymbolsGroup;

    @NotBlank(message = "Ikkinchi belgilar guruhi yuborilmadi!")
    private String secondSymbolsGroup;

    @NotBlank(message = "Uchinchi belgilar guruhi yuborilmadi!")
    private String thirdSymbolsGroup;

    @SkipDb
    @NotNull(message = "Obyekt joylashgan viloyat yuborilmadi!")
    private Integer regionId;

    @SkipDb
    @NotNull(message = "Obyekt joylashgant tuman yuborilmadi!")
    private Integer districtId;

    @SkipDb
    @NotBlank(message = "Obyekt joylashgan manzil yuborilmadi")
    private String address;

    @SkipDb
    @NotBlank(message = "Ekspertiza xulosasi fayli yuborilmadi!")
    private String expertiseConclusionPath;

    @NotBlank(message = "Ekspertiza xulosasi raqami yuborilmadi!")
    private String expertiseConclusionNumber;

    @Schema(hidden = true)
    private String certificateNumber;

    @Schema(hidden = true)
    private LocalDate certificateDate;

    @Schema(hidden = true)
    private LocalDate certificateValidityDate;

    @Schema(hidden = true)
    @Enumerated(EnumType.STRING)
    private List<AccreditationSphere> accreditationSpheres;

    @Schema(hidden = true)
    private Map<String, FileDto> files = new HashMap<>();

    public void buildFiles() {
        files.put("expertiseConclusionPath", new FileDto(expertiseConclusionPath, null));
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_EXPERTISE_CONCLUSION;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }
}
