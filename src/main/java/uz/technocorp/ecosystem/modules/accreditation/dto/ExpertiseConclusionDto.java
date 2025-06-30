package uz.technocorp.ecosystem.modules.accreditation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public class ExpertiseConclusionDto {

    @NotNull(message = "Buyurtamachi STIRi yuborilmadi!")
    private Long tin;
    @NotNull(message = "Ekspertiza xulosasini hisobga olish uchun topshirgan sana yuborilmadi!")
    private LocalDate submissionDate;
    @NotNull(message = "Kuzatuv xati sanasi yuborilmadi!")
    private LocalDate monitoringLetterDate;
    @NotBlank(message = "Kuzatuv xati raqami yuborilmadi!")
    private String monitoringLetterNumber;
    @NotBlank(message = "Ekspertiza obyekti nomi yuborilmadi!")
    private String expertiseObjectName;
    @NotBlank(message = "Birinchi belgilar guruhi yuborilmadi!")
    private String firstSymbolsGroup;
    @NotBlank(message = "Ikkinchi belgilar guruhi yuborilmadi!")
    private String secondSymbolsGroup;
    @NotBlank(message = "Uchinchi belgilar guruhi yuborilmadi!")
    private String thirdSymbolsGroup;
    @NotBlank(message = "Obyekt manzili yuborilmadi!")
    private String objectAddress;
    @NotNull(message = "Viloyat yuborilmadi!")
    private Integer regionId;
    @NotNull(message = "Tuman yuborilmadi!")
    private Integer districtId;
    @NotBlank(message = "Ekspertiza xulosasi fayli yuborilmadi!")
    private String expertiseConclusionPath;
    @NotBlank(message = "Ekspertiza xulosasi raqami yuborilmadi!")
    private String expertiseConclusionNumber;
}
