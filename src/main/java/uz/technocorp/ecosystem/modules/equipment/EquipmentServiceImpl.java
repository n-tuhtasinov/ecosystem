package uz.technocorp.ecosystem.modules.equipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final AppealRepository appealRepository;
    private final ProfileRepository profileRepository;

    @Override
    public void create(EquipmentRegistryDto equipmentRegistryDto) {
        Appeal appeal = appealRepository.findById(equipmentRegistryDto.appealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", equipmentRegistryDto.appealId()));
        Profile profile = profileRepository.findByTin(appeal.getLegalTin()).orElseThrow(() -> new ResourceNotFoundException("Profile", "STIR", appeal.getLegalTin()));

        EquipmentDto dto = parseJsonToObject(appeal.getData());
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType());

        Equipment equipment = Equipment.builder()
                .type(info.equipmentType())
                .appealId(appeal.getId())
                .registryNumber(info.registryNumber())
                .orderNumber(info.orderNumber())
                .legalTin(appeal.getLegalTin())
                .legalName(profile.getLegalName())
                .hazardousFacilityId(dto.hazardousFacilityId())
                .childEquipmentId(dto.childEquipmentId())
                .factoryNumber(dto.factoryNumber())
                .regionId(appeal.getRegionId())
                .regionName(appeal.getRegionName())
                .districtId(appeal.getDistrictId())
                .districtName(appeal.getDistrictName())
                .address(appeal.getAddress())
                .model(dto.model())
                .factory(dto.factory())
                .location(dto.location())
                .manufacturedAt(dto.manufacturedAt())
                .partialCheckDate(dto.partialCheckDate())
                .fullCheckDate(dto.fullCheckDate())
                .boomLength(dto.boomLength())
                .liftingCapacity(dto.liftingCapacity())
                .capacity(dto.capacity())
                .environment(dto.environment())
                .pressure(dto.pressure())
                .sphere(dto.sphere())
                .stopCount(dto.stopCount())
                .length(dto.length())
                .speed(dto.speed())
                .height(dto.height())
                .passengersPerMinute(dto.passengersPerMinute())
                .passengerCount(dto.passengerCount())
                .diameter(dto.diameter())
                .thickness(dto.thickness())
                .attractionName(dto.attractionName())
                .acceptedAt(dto.acceptedAt())
                .childEquipmentSortId(dto.childEquipmentSortId())
                .country(dto.country())
                .servicePeriod(dto.servicePeriod())
                .riskLevel(dto.riskLevel())
                .parentOrganization(dto.parentOrganization())
                .nonDestructiveCheckDate(dto.nonDestructiveCheckDate())
                .temperature(dto.temperature())
                .density(dto.density())
                .fuel(dto.fuel())
                .labelPath(dto.labelPath())
                .description(dto.description())
                .inspectorId(appeal.getInspectorId())
                .saleContractPath(dto.saleContractPath())
                .equipmentCertPath(dto.equipmentCertPath())
                .assignmentDecreePath(dto.assignmentDecreePath())
                .expertisePath(dto.expertisePath())
                .installationCertPath(dto.installationCertPath())
                .additionalFilePath(dto.additionalFilePath())
                .passportPath(dto.passportPath())
                .techReadinessActPath(dto.techReadinessActPath())
                .seasonalReadinessActPath(dto.seasonalReadinessActPath())
                .safetyDecreePath(dto.safetyDecreePath())
                .gasSupplyProjectPath(dto.gasSupplyProjectPath())
                .build();

        equipmentRepository.save(equipment);
    }

    private EquipmentInfoDto getEquipmentInfoByAppealType(AppealType appealType) {
        return switch (appealType) {
            case REGISTER_CRANE -> getInfo(EquipmentType.CRANE, "P");
            case REGISTER_CONTAINER -> getInfo(EquipmentType.CONTAINER, "C");
            default -> throw new RuntimeException("Ariza turi hech bir qurilma turiga mos kelmadi");
        };
    }

    private EquipmentInfoDto getInfo(EquipmentType equipmentType, String label) {
        long orderNumber = equipmentRepository.getMax(equipmentType).orElse(0L) + 1;
        return new EquipmentInfoDto(equipmentType, label + orderNumber, orderNumber);
    }

    private EquipmentDto parseJsonToObject(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.treeToValue(jsonNode, EquipmentDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON deserialization error", e);
        }
    }

}
