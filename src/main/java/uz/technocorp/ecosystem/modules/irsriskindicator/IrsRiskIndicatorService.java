package uz.technocorp.ecosystem.modules.irsriskindicator;

import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.irsriskindicator.dto.IrsRiskIndicatorDto;

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
    void attachFile(UUID id, FilePathDto dto);
    void cancelRiskIndicator(UUID id);
    List<RiskIndicatorView> findAllByIrsIdAndTin(UUID id, Long tin);
    List<RiskIndicatorView> findAllByTin(Long tin);
}
