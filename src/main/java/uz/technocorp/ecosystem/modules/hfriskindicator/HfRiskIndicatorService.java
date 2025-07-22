package uz.technocorp.ecosystem.modules.hfriskindicator;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
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

    void create(List<HFRIndicatorDto> dtoList);
    void success(List<HFRIndicatorDto> dtoList);
    void update(UUID id, HFRIndicatorDto dto);
    void delete(UUID id);
    void attachFile(UUID id, FilePathDto dto);
    void cancelRiskIndicator(UUID id);
    List<RiskIndicatorView> findAllByHFIdAndTin(UUID id, Long tin, Integer intervalId);
    List<RiskIndicatorView> findAllByTin(Long tin, Integer intervalId);

    List<RiskIndicatorView> findAllToFixByTin(Long tin, UUID hfId, Integer intervalId);
}
