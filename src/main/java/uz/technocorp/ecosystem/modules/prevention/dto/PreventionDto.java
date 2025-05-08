package uz.technocorp.ecosystem.modules.prevention.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreventionDto {

    @PastOrPresent(message = "Profilaktika sanasi kelajakda bo'lishi mumkin emas")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "Profilaktika shakli jo'natilmadi")
    private String type;

    private String content;

    @Valid
    @NotEmpty(message = "Fayllar roʻyxati bo'sh bo'lmasligi kerak")
    private List<@NotBlank(message = "Fayl url bo'sh bo'lmasligi kerak") String> filePaths;

    @NotNull(message = "Tashkilot INN si jo'natilmadi")
    private Long tin;

}
