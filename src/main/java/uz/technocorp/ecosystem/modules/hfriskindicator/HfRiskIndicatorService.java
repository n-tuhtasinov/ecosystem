package uz.technocorp.ecosystem.modules.hfriskindicator;

import uz.technocorp.ecosystem.modules.hfriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface HfRiskIndicatorService {

    void create(HFRIndicatorDto dto);
    void update(UUID id, HFRIndicatorDto dto);
    void delete(UUID id);
    List<RiskIndicatorView> findAllByHFIdAndTin(UUID id, Long tin);
    List<RiskIndicatorView> findAllByTin(Long tin);
}
