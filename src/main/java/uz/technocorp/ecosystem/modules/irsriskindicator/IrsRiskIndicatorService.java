package uz.technocorp.ecosystem.modules.irsriskindicator;

import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.HFRiskIndicatorView;
import uz.technocorp.ecosystem.modules.irsriskindicator.dto.IrsRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.irsriskindicator.view.IrsRiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface IrsRiskIndicatorService {

    void create(IrsRiskIndicatorDto dto);
    void update(UUID id, IrsRiskIndicatorDto dto);
    void delete(UUID id);
    List<IrsRiskIndicatorView> findAllByIrsId(UUID id);
    List<IrsRiskIndicatorView> findAllByTin(Long tin);
}
