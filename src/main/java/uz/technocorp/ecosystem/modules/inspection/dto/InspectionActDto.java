package uz.technocorp.ecosystem.modules.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InspectionActDto {

    @NotNull(message = "Tekshiruv ID si jo'natilmadi")
    private UUID inspectionId;

    @NotBlank(message = "Tuman yoki shahar nomi yuborilmadi")
    private String districtName;

    @NotBlank(message = "Tekshirilayotgan XICHOlar, Qurilmalar yoki INMlar nomlari yuborilmadi")
    private String objects;

    @NotBlank(message = "1-bo'lim ma'lumoti yuborilmadi")
    private String sectionFirst;

    @NotBlank(message = "2-bo'lim ma'lumoti yuborilmadi")
    private String sectionSecond;

    @NotBlank(message = "4-bo'lim ma'lumoti yuborilmadi")
    private String sectionFourth;

    @NotBlank(message = "5-bo'lim ma'lumoti yuborilmadi")
    private String sectionFifth;

    @NotBlank(message = "6-bo'lim ma'lumoti yuborilmadi")
    private String sectionSixth;
}
