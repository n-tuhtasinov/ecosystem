package uz.technocorp.ecosystem.modules.hazardousfacility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
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
    private final AppealRepository appealRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Override
    public void create(UUID id) {

        Appeal appeal = appealRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        Integer maxSerialNumber = repository.findMaxSerialNumber();
        District district = districtRepository
                .findById(appeal.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", id));
        Region region = regionRepository
                .findById(appeal.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", id));
        String registryNumber = String.format("%05d", maxSerialNumber) + "-" + String.format("%04d", district.getNumber()) + "-" + String.format("%02d", region.getNumber());
        HfAppealDto hfAppealDto = parseJsonData(appeal.getData());
        repository.save(
                HazardousFacility.builder()
                        .legalTin(appeal.getLegalTin())
                        .legalName(appeal.getLegalName())
                        .regionId(appeal.getRegionId())
                        .districtId(appeal.getDistrictId())
                        .profileId(appeal.getProfileId())
                        .legalAddress(appeal.getLegalAddress())
                        .phoneNumber(appeal.getPhoneNumber())
                        .email(hfAppealDto.getEmail())
                        .upperOrganization(hfAppealDto.getUpperOrganization())
                        .name(hfAppealDto.getName())
                        .address(hfAppealDto.getAddress())
                        .appealId(appeal.getId())
                        .hazardousFacilityTypeId(hfAppealDto.getHazardousFacilityTypeId())
                        .extraArea(hfAppealDto.getExtraArea())
                        .description(hfAppealDto.getDescription())
                        .registryNumber(registryNumber)
                        .active(true)
                        .build());
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

    private HfAppealDto parseJsonData(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.treeToValue(jsonNode, HfAppealDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON deserialization error", e);
        }
    }
}
