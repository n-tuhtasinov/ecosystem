package uz.technocorp.ecosystem.modules.irs;

import org.springframework.web.bind.annotation.PathVariable;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDeregisterDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsRegistryDto;

import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.04.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceService {

    void create(IrsRegistryDto dto);
//    void create(IrsDto dto);
    void update(UUID id, IrsDto dto);
    void deregister(UUID id, IrsDeregisterDto dto);
}
