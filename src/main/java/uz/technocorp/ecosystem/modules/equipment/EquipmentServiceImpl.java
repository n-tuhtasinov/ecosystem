package uz.technocorp.ecosystem.modules.equipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSortService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private final ProfileRepository profileRepository;
    private final DistrictService districtService;
    private final AttachmentService attachmentService;
    private final TemplateService templateService;
    private final ChildEquipmentService childEquipmentService;
    private final ChildEquipmentSortService childEquipmentSortService;

    @Override
    public void create(Appeal appeal) {
        Profile profile = profileRepository.findByTin(appeal.getLegalTin()).orElseThrow(() -> new ResourceNotFoundException("Profile", "STIR", appeal.getLegalTin()));

        EquipmentDto dto = parseJsonToObject(appeal.getData());
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType());

        /*// Create PDF with parameters
        String registryFilepath = dto.type().equals(EquipmentType.ATTRACTION_PASSPORT)
                ? createAttractionPassportPdf(dto, profile.getLegalAddress()) // Attraction Passport
                : createEquipmentPdf(dto, profile.getLegalAddress()); // Other Equipments*/

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
                .districtId(appeal.getDistrictId())
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
                .inspectorId(appeal.getExecutorId())
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
//                .registryFilePath(registryFilepath)
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

    private String createAttractionPassportPdf(EquipmentDto dto, String legalAddress) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("attractionName", dto.attractionName());
        parameters.put("attractionType", childEquipmentService.getById(dto.childEquipmentId()).getName());
        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("legalName", dto.legalName());
        parameters.put("legalTin", dto.legalTin().toString());
        parameters.put("legalAddress", legalAddress);
        parameters.put("registryNumber", dto.number());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("factory", dto.factory());
        parameters.put("regionName", dto.regionName());
        parameters.put("districtName", districtService.getDistrict(dto.districtId()).getName());
        parameters.put("address", dto.address());
        parameters.put("riskLevel", dto.riskLevel().value);

        String content = getTemplateContent(TemplateType.REGISTRY_ATTRACTION);

        return attachmentService.createPdfFromHtml(content, "reestr/attraction", parameters, false);
    }

    private String createEquipmentPdf(EquipmentDto dto, String legalAddress) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("legalAddress", legalAddress);
        parameters.put("equipmentType", childEquipmentService.getById(dto.childEquipmentId()).getName());
        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
        parameters.put("legalTin", dto.legalTin().toString());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("factory", dto.factory());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("number", dto.number());
        parameters.put("registrationDate", LocalDate.now().toString());
        parameters.put("address", dto.address());
        parameters.put("parameters", "PARAMETERS"); // TODO

        String content = getTemplateContent(TemplateType.REGISTRY_EQUIPMENT);

        return attachmentService.createPdfFromHtml(content, "reestr/equipment", parameters, false);
    }

    private String getTemplateContent(TemplateType type) {
        return templateService.getByType(type.name()).getContent();
    }
}
