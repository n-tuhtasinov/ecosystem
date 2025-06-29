package uz.technocorp.ecosystem.modules.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 23.05.2025
 * @since v1.0
 */
public record InspectionDto(
        @NotNull(message = "Tekshiruv boshlanish sanasi kiritilmadi!") LocalDate startDate,

        @NotNull(message = "Tekshiruv tugash sanasi kiritilmadi!") LocalDate endDate,

        @NotNull(message = "Tashkilot STIRi kiritilmadi!") Long tin,

        @NotNull(message = "Inspektorlar ro'yxati bo‘sh bo‘lishi mumkin emas")
        @Size(min = 1, message = "Kamida bitta inspektor tanlanishi kerak")
        List<@NotNull(message = "Inspektor ID bo‘sh bo‘lmasligi kerak") UUID> inspectorIdList,

        @NotNull(message = "Interval ID bo‘sh bo‘lishi mumkin emas")
        Integer intervalId,

        @NotBlank(message = "Buyruq fayl yo‘li bo‘sh bo‘lishi mumkin emas")
        String decreePath,

        @NotNull(message = "Buyruq sanasi bo‘sh bo‘lishi mumkin emas")
        LocalDate decreeDate,

        @NotBlank(message = "Buyruq raqami bo‘sh bo‘lishi mumkin emas")
        String decreeNumber) {
}
