package uz.technocorp.ecosystem.modules.inspectionreportexecution.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public record IRExecutionDto( @NotBlank(message = "Yuborilgan qiymat bo'sh bo'lmasligi kerak!") String paramValue) {
}
