package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator;

import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.HFRiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface HazardousFacilityRiskIndicatorService {

    void create(HFRIndicatorDto dto);
    void update(UUID id, HFRIndicatorDto dto);
    void delete(UUID id);
    List<HFRiskIndicatorView> findAllByHazardousFacilityId(UUID id);
    List<HFRiskIndicatorView> findAllByTin(Long tin);
}
