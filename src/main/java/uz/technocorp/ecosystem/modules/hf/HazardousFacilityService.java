package uz.technocorp.ecosystem.modules.hf;

import uz.technocorp.ecosystem.modules.hf.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfRegistryDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityService {

    void create(HfRegistryDto dto);
    void create(HfDto dto);
    void update(UUID id, HfDto dto);
    void deregister(UUID id, HfDeregisterDto dto);
    void periodicUpdate(UUID id, HfPeriodicUpdateDto dto);
}
