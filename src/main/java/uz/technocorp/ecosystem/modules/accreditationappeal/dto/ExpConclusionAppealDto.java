package uz.technocorp.ecosystem.modules.accreditationappeal.dto;

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
    @Schema(hidden = true)
    private String phoneNumber;

    @SkipDb
    @Schema(hidden = true)
    private String address;

    @SkipDb
    @NotNull(message = "Buyurtamachi STIRi yuborilmadi!")
    private Long customerTin;
    @SkipDb
    @NotNull(message = "Ekspertiza xulosasini hisobga olish uchun topshirgan sana yuborilmadi!")
    private LocalDate submissionDate;
    @SkipDb
    @NotNull(message = "Kuzatuv xati sanasi yuborilmadi!")
    private LocalDate monitoringLetterDate;
    @SkipDb
    @NotBlank(message = "Kuzatuv xati raqami yuborilmadi!")
    private String monitoringLetterNumber;
    @SkipDb
    @NotBlank(message = "Ekspertiza obyekti nomi yuborilmadi!")
    private String expertiseObjectName;
    @SkipDb
    @NotBlank(message = "Birinchi belgilar guruhi yuborilmadi!")
    private String firstSymbolsGroup;
    @SkipDb
    @NotBlank(message = "Ikkinchi belgilar guruhi yuborilmadi!")
    private String secondSymbolsGroup;
    @SkipDb
    @NotBlank(message = "Uchinchi belgilar guruhi yuborilmadi!")
    private String thirdSymbolsGroup;
    @SkipDb
    @NotBlank(message = "Obyekt manzili yuborilmadi!")
    private String objectAddress;
    @SkipDb
    @NotNull(message = "Viloyat yuborilmadi!")
    private Integer regionId;

    @SkipDb
    @Schema(hidden = true)
    private String regionName;
    @SkipDb
    @NotNull(message = "Tuman yuborilmadi!")
    private Integer districtId;
    @SkipDb
    @Schema(hidden = true)
    private String districtName;
    @SkipDb
    @NotBlank(message = "Ekspertiza xulosasi fayli yuborilmadi!")
    private String expertiseConclusionPath;
    @SkipDb
    @NotBlank(message = "Ekspertiza xulosasi raqami yuborilmadi!")
    private String expertiseConclusionNumber;
    @SkipDb
    @NotBlank(message = "Mas'ul vakilning ism sharifi yuborilmadi!")
    private String responsiblePersonName;

    @SkipDb
    private String customerLegalName;
    @SkipDb
    private String customerLegalForm;
    @SkipDb
    private String customerLegalAddress;
    @SkipDb
    private String customerPhoneNumber;
    @SkipDb
    private String customerFullName;

    @SkipDb
    private String certificateNumber;

    @SkipDb
    private LocalDate certificateDate;

    @SkipDb
    private LocalDate certificateValidityDate;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_EXPERTISE_CONCLUSION;
    }

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();



    public void buildFiles() {
        files.put("expertiseConclusionPath", expertiseConclusionPath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
