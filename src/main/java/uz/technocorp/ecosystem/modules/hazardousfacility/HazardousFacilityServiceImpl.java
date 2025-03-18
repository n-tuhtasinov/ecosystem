package uz.technocorp.ecosystem.modules.hazardousfacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.HazardousFacilityRegistrationAppeal;
import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.HazardousFacilityRegistrationAppealRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;

import java.util.Map;
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
    private final HazardousFacilityRegistrationAppealRepository appealRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Override
    public void create(UUID id) {

        HazardousFacilityRegistrationAppeal hazardousFacilityRegistrationAppeal = appealRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        Integer maxSerialNumber = repository.findMaxSerialNumber();
        District district = districtRepository
                .findById(hazardousFacilityRegistrationAppeal.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", id));
        Region region = regionRepository
                .findById(hazardousFacilityRegistrationAppeal.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", id));
        String registryNumber = String.format("%05d", maxSerialNumber) + "-" + String.format("%04d", district.getNumber()) + "-" + String.format("%02d", region.getNumber());
        repository.save(
                new HazardousFacility(
                        hazardousFacilityRegistrationAppeal.getLegalTin(),
                        hazardousFacilityRegistrationAppeal.getLegalName(),
                        hazardousFacilityRegistrationAppeal.getRegionId(),
                        hazardousFacilityRegistrationAppeal.getDistrictId(),
                        hazardousFacilityRegistrationAppeal.getProfileId(),
                        hazardousFacilityRegistrationAppeal.getLegalAddress(),
                        hazardousFacilityRegistrationAppeal.getPhoneNumber(),
                        hazardousFacilityRegistrationAppeal.getEmail(),
                        hazardousFacilityRegistrationAppeal.getUpperOrganization(),
                        hazardousFacilityRegistrationAppeal.getName(),
                        hazardousFacilityRegistrationAppeal.getAddress(),
                        hazardousFacilityRegistrationAppeal.getId(),
                        hazardousFacilityRegistrationAppeal.getHazardousFacilityTypeId(),
                        hazardousFacilityRegistrationAppeal.getExtraArea(),
                        hazardousFacilityRegistrationAppeal.getDescription(),
                        registryNumber
                )
        );
    }

    @Override
    public void deActivate(UUID id, Map<String, String> dto) {
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", id));
        hazardousFacility.setActive(false);
        hazardousFacility.setDeregistrationFilePath(dto.get("deregistrationFilePath"));
        hazardousFacility.setDeregistrationFilePath(dto.get("deregistrationReason"));
        hazardousFacility.setRegistryNumber(
                hazardousFacility.getRegistryNumber() + "/р-ч" + String.format("%05d", hazardousFacility.getSerialNumber())
        );
        repository.save(hazardousFacility);
    }

    @Override
    public void periodicUpdate(UUID id, Map<String, String> dto) {
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", id));
        hazardousFacility.setDeregistrationFilePath(dto.get("periodicUpdateFilePath"));
        hazardousFacility.setDeregistrationFilePath(dto.get("periodicUpdateReason"));
        hazardousFacility.setRegistryNumber(
                hazardousFacility.getRegistryNumber() + "/д-я" + String.format("%05d", hazardousFacility.getSerialNumber())
        );
        repository.save(hazardousFacility);
    }
}
