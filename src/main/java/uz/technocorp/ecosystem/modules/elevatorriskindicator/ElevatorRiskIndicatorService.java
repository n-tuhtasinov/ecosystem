package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface ElevatorRiskIndicatorService {

    void create(EquipmentRiskIndicatorDto dto);
    void update(UUID id, EquipmentRiskIndicatorDto dto);
    void delete(UUID id);
    List<RiskIndicatorView> findAllByEquipmentIdAndTin(UUID id, Long tin);
    List<RiskIndicatorView> findAllByTin(Long tin);
}
