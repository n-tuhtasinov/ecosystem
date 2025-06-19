package uz.technocorp.ecosystem.modules.prevention.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @NotNull(message = "Tashkilot INN si jo'natilmadi")
    private Long tin;

    @PastOrPresent(message = "Profilaktika sanasi kelajakda bo'lishi mumkin emas")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Profilaktika shakli jo'natilmadi")
    private Integer typeId;

    private String content;

    private String eventFile;
    private String organizationFile;
}
