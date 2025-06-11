package uz.technocorp.ecosystem.modules.assigninspectorhf;

import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AIDto;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AssignInfoDto;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public interface AIHFService {

    void create(AIDto dto);
    void update(UUID id, AIDto dto);
    AssignInfoDto getInspectorInfo(UUID assignId);
}
