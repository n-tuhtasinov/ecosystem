package uz.technocorp.ecosystem.modules.hazardousfacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.HazardousFacilityAppeal;
import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.HazardousFacilityAppealRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.03.2025
 * @since v1.0
 */

@Service
@RequiredArgsConstructor
public class HazardousFacilityServiceImpl implements HazardousFacilityService {

    private final HazardousFacilityRepository repository;
    private final HazardousFacilityAppealRepository appealRepository;

    @Override
    public void create(UUID id) {

        HazardousFacilityAppeal hazardousFacilityAppeal = appealRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));

        repository.save(
                new HazardousFacility(
                        hazardousFacilityAppeal.getLegal_tin(),
                        hazardousFacilityAppeal.getLegalName(),
                        hazardousFacilityAppeal.getRegionId(),
                        hazardousFacilityAppeal.getDistrictId(),
                        hazardousFacilityAppeal.getProfileId(),
                        hazardousFacilityAppeal.getLegalAddress(),
                        hazardousFacilityAppeal.getPhoneNumber(),
                        hazardousFacilityAppeal.getEmail(),
                        hazardousFacilityAppeal.getUpperOrganization(),
                        hazardousFacilityAppeal.getName(),
                        hazardousFacilityAppeal.getAddress(),
                        hazardousFacilityAppeal.getId(),
                        hazardousFacilityAppeal.getDangerousObjectTypeId(),
                        hazardousFacilityAppeal.getExtraArea(),
                        hazardousFacilityAppeal.getDescription(),
                        hazardousFacilityAppeal.getObjectNumber()
                )
        );
    }
}
