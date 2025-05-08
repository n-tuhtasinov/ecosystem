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
                .hazardousFacilityId(dto.getHazardousFacilityId())
                .childEquipmentId(dto.getChildEquipmentId())
                .factoryNumber(dto.getFactoryNumber())
                .regionId(appeal.getRegionId())
                .regionName(appeal.getRegionName())
                .districtId(appeal.getDistrictId())
                .districtName(appeal.getDistrictName())
                .address(appeal.getAddress())
                .model(dto.getModel())
                .factory(dto.getFactory())
                .location(dto.getLocation())
                .manufacturedAt(dto.getManufacturedAt())
                .partialCheckDate(dto.getPartialCheckDate())
                .fullCheckDate(dto.getFullCheckDate())
                .boomLength(dto.getBoomLength())
                .liftingCapacity(dto.getLiftingCapacity())
                .capacity(dto.getCapacity())
                .environment(dto.getEnvironment())
                .pressure(dto.getPressure())
                .sphere(dto.getSphere())
                .stopCount(dto.getStopCount())
                .length(dto.getLength())
                .speed(dto.getSpeed())
                .height(dto.getHeight())
                .passengersPerMinute(dto.getPassengersPerMinute())
                .passengerCount(dto.getPassengerCount())
                .diameter(dto.getDiameter())
                .thickness(dto.getThickness())
                .attractionName(dto.getAttractionName())
                .acceptedAt(dto.getAcceptedAt())
                .childEquipmentSortId(dto.getChildEquipmentSortId())
                .country(dto.getCountry())
                .servicePeriod(dto.getServicePeriod())
                .riskLevel(dto.getRiskLevel())
                .parentOrganization(dto.getParentOrganization())
                .nonDestructiveCheckDate(dto.getNonDestructiveCheckDate())
                .temperature(dto.getTemperature())
                .density(dto.getDensity())
                .fuel(dto.getFuel())
                .labelPath(dto.getLabelPath())
                .description(dto.getDescription())
                .inspectorId(appeal.getInspectorId())
                .saleContractPath(dto.getSaleContractPath())
                .equipmentCertPath(dto.getEquipmentCertPath())
                .assignmentDecreePath(dto.getAssignmentDecreePath())
                .expertisePath(dto.getExpertisePath())
                .installationCertPath(dto.getInstallationCertPath())
                .additionalFilePath(dto.getAdditionalFilePath())
                .passportPath(dto.getPassportPath())
                .techReadinessActPath(dto.getTechReadinessActPath())
                .seasonalReadinessActPath(dto.getSeasonalReadinessActPath())
                .safetyDecreePath(dto.getSafetyDecreePath())
                .gasSupplyProjectPath(dto.getGasSupplyProjectPath())
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
