package uz.technocorp.ecosystem.modules.inspection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.06.2025
 * @since v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectorShortInfo {

    private UUID id;
    private String name;
}
