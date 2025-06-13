package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
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

    void create(List<EquipmentRiskIndicatorDto> dto);
    void update(UUID id, EquipmentRiskIndicatorDto dto);
    void delete(UUID id);
    void attachFile(UUID id, FilePathDto dto);
    void cancelRiskIndicator(UUID id);
    List<RiskIndicatorView> findAllByEquipmentIdAndTin(UUID id, Long tin, Integer intervalId);
    List<RiskIndicatorView> findAllByTin(Long tin, Integer intervalId);
    Page<RiskIndicatorView> findAllToFixByTin(Long tin, Integer intervalId, int page, int size);
}
