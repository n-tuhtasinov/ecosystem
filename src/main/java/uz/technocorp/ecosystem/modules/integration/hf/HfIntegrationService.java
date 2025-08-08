package uz.technocorp.ecosystem.modules.integration.hf;

import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;
import uz.technocorp.ecosystem.modules.integration.hf.dto.HfInfoDto;

/**
 * @author Suxrob
 * @version 1.0
 * @created 08.08.2025
 * @since v1.0
 */
public interface HfIntegrationService {
    InfoDto<HfInfoDto> getHfInfo(String tin);
}
