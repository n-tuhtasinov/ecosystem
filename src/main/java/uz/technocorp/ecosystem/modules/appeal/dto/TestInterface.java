package uz.technocorp.ecosystem.modules.appeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 04.06.2025
 * @since v1.0
 */
public interface TestInterface {

    @Schema(hidden = true)
    Map<String, String> getFiles();
}
