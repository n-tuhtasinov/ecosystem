package uz.technocorp.ecosystem.modules.prevention.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

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
public class PreventionParamsDto {

    private Boolean isPassed = false;
    private String search;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean viewed = false;
    private Integer regionId;
    private Integer districtId;
    private UUID inspectorId;
    private Integer year = LocalDate.now().getYear();

    @Min(value = 1, message = "Page 0 dan katta bo'lishi kerak")
    private Integer page = 1;

    @Min(value = 1, message = "Page 0 dan katta bo'lishi kerak")
    @Max(value = 100, message = "Size maksimal 100 gacha bo'lishi mumkin")
    private Integer size = 12;

    // District validation
   /* @Schema(hidden = true)
    @AssertTrue(message = "Tuman tanlanganda, viloyat ham tanlanishi kerak")
    public boolean isDistrictRegionValid() {
        if (districtId != null) {
            return regionId != null;
        }
        return true;
    }*/

    // Date validation
    @Schema(hidden = true)
    @AssertTrue(message = "Qidiruvning yakuniy sanasi boshlang'ich sanadan keyin bo'lishi kerak")
    public boolean isDateRangeValid() {
        if (startDate != null && endDate != null) {
            return !endDate.isBefore(startDate);
        }
        return true;
    }
}
