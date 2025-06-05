package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.04.2025
 * @since v1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class EquipmentAppealDto implements AppealDto {

    @NotBlank(message = "Telefon nomer jo'natilmadi")
    private String phoneNumber;

    private UUID hazardousFacilityId;

    @Schema(hidden = true)
    private String hazardousFacilityName;

    @NotNull(message = "Qurilma turi tanlanmadi")
    private Integer childEquipmentId;

    @Schema(hidden = true)
    private String childEquipmentName;

    @NotBlank(message = "Zavod raqami jo'natilmadi")
    private String factoryNumber;

    @NotNull(message = "Qurilma joylashgan viloyat tanlanmadi")
    private Integer regionId;

    @NotNull(message = "Qurilma joylashgan tuman tanlanmadi")
    private Integer districtId;

    @NotBlank(message = "Qurilma joylashgan manzil jo'natilmadi")
    private String address;

    @NotBlank(message = "Qurilma model(marka)si jo'natilmadi ")
    private String model;

    @NotBlank(message = "Ishlan chiqargan zavod nomi jo'natilmadi ")
    private String factory;

    @NotBlank(message = "Qurilma joylashgan giolokatsiya jo'natilmadi")
    private String location;

    @NotNull(message = "Ishlab chiqarilgan sana jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturedAt;

    @NotNull(message = "Qisman texnik/tashqi va ichki ko'rik sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate partialCheckDate;

    @NotNull(message = "To'liq texnik ko'rik/gidrosinov/keyingi tekshirish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fullCheckDate;

    @SkipDb
    @NotBlank(message = "Qurilmaning birkasi bilan surati pathi jo'natilmadi")
    private String labelPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning oldi-sotdi shartnomasi pathi jo'natilmadi")
    private String saleContractPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning sertifikati fayli pathi jo'natilmadi")
    private String equipmentCertPath;

    @SkipDb
    @NotBlank(message = "Qurilmaning mas'ul shaxs tayinlanganligi to'g'risidagi buyrug'i pathi jo'natilmadi")
    private String assignmentDecreePath;

    @SkipDb
    @NotBlank(message = "Qurilmaning expertiza loyihasi pathi jo'natilmadi")
    private String expertisePath;

    @SkipDb
    @NotBlank(message = "Qurilmaning montaj guvohnomasi fayli pathi jo'natilmadi")
    private String installationCertPath;

    @SkipDb
    private String additionalFilePath;

    @Schema(hidden = true)
    private Map<String, String> files = new HashMap<>();

    @Schema(hidden = true)
    private Map<String, String> parameters = new HashMap<>();

    public void buildFiles() {
        files.put("labelPath", labelPath);
        files.put("saleContractPath", saleContractPath);
        files.put("equipmentCertPath", equipmentCertPath);
        files.put("assignmentDecreePath", assignmentDecreePath);
        files.put("expertisePath", expertisePath);
        files.put("installationCertPath", installationCertPath);
        files.put("additionalFilePath", additionalFilePath);
    }

    @AssertTrue
    public boolean isFilesBuilt() {
        buildFiles();
        return true;
    }


}
