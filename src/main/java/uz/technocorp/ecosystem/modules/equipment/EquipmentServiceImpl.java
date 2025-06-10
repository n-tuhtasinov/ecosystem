package uz.technocorp.ecosystem.modules.equipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSortService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;

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

    private final RegionService regionService;
    private final ProfileService profileService;
    private final TemplateService templateService;
    private final DistrictService districtService;
    private final AttachmentService attachmentService;
    private final ChildEquipmentService childEquipmentService;
    private final ChildEquipmentSortService childEquipmentSortService;
    private final EquipmentRepository equipmentRepository;

    @Override
    public void create(Appeal appeal) {
        Profile profile = profileService.findByTin(appeal.getLegalTin());

        EquipmentDto dto = parseJsonToObject(appeal.getData());
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType());

        // Create registry (reestr) PDF with parameters
        String registryFilepath = EquipmentType.ATTRACTION_PASSPORT.equals(info.equipmentType())
                ? createAttractionPassportPdf(appeal, dto, info) // Attraction Passport
                : createEquipmentPdf(appeal, dto, info); // All Equipments

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
                .parameters(dto.parameters())
                .sphere(dto.sphere())
                .attractionName(dto.attractionName())
                .acceptedAt(dto.acceptedAt())
                .childEquipmentSortId(dto.childEquipmentSortId())
                .country(dto.country())
                .servicePeriod(dto.servicePeriod())
                .riskLevel(dto.riskLevel())
                .parentOrganization(dto.parentOrganization())
                .nonDestructiveCheckDate(dto.nonDestructiveCheckDate())
                .files(dto.files())
                .description(dto.description())
                .inspectorId(appeal.getExecutorId())
                .registryFilePath(registryFilepath)
                .registrationDate(LocalDate.now())
                .build();

        equipmentRepository.save(equipment);
    }

    @Override
    public Page<EquipmentView> getAll(User user, EquipmentParams params) {
        return equipmentRepository.getAllByParams(user, params);
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
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        try {
            return mapper.treeToValue(jsonNode, EquipmentDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON deserialization error", e);
        }
    }

    private String createAttractionPassportPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("attractionName", dto.attractionName());
        parameters.put("attractionType", childEquipmentService.getById(dto.childEquipmentId()).getName());
        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("legalAddress", appeal.getLegalAddress());
        parameters.put("registryNumber", info.registryNumber());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("factory", dto.factory());
        parameters.put("regionName", regionService.getById(appeal.getRegionId()).getName());
        parameters.put("districtName", districtService.getDistrict(appeal.getDistrictId()).getName());
        parameters.put("address", appeal.getAddress());
        parameters.put("riskLevel", dto.riskLevel().value);

        String content = getTemplateContent(TemplateType.REGISTRY_ATTRACTION);

        return attachmentService.createPdfFromHtml(content, "reestr/attraction", parameters, false);
    }

    private String createEquipmentPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("legalAddress", appeal.getLegalAddress());
        parameters.put("equipmentType", childEquipmentService.getById(dto.childEquipmentId()).getName());
        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("factory", dto.factory());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("number", info.registryNumber());
        parameters.put("registrationDate", LocalDate.now().toString());
        parameters.put("address", appeal.getAddress());
        parameters.put("parameters", dto.parameters().toString()); // TODO parameter lar bilan muammo bo'lishi mumkin

        String content = getTemplateContent(TemplateType.REGISTRY_EQUIPMENT);

        return attachmentService.createPdfFromHtml(content, "reestr/equipment", parameters, false);
    }

    private String getTemplateContent(TemplateType type) {
        return templateService.getByType(type.name()).getContent();
    }
}
