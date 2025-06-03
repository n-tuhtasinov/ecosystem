package uz.technocorp.ecosystem.modules.irs;

import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDeregisterDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.04.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceService {

    void create(Appeal appeal);
    void update(UUID id, IrsDto dto);
    void deregister(UUID id, IrsDeregisterDto dto);
}
