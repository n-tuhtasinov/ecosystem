package uz.technocorp.ecosystem.modules.declaration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeclarationParams {

    private String search;

    @Schema(hidden = true)
    private Long legalTin;

    private Integer regionId;

    private Integer districtId;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;
}
