package uz.technocorp.ecosystem.modules.equipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentViewById;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.util.UUID;

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
    private final ProfileService profileService;
    private final OfficeService officeService;

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
//                .registryFilePath(registryFilepath)
                .registrationDate(LocalDate.now())
                .build();

        equipmentRepository.save(equipment);
    }

    @Override
    public Page<EquipmentView> getAll(User user, EquipmentParams params) {

        Profile profile = profileService.getProfile(user.getProfileId());

        if (user.getRole() == Role.INSPECTOR || user.getRole() == Role.REGIONAL) {
            Office office = officeService.findById(profile.getOfficeId());
            if (params.getRegionId() != null){
                if (!params.getRegionId().equals(office.getRegionId())){
                    throw new RuntimeException("Sizga bu viloyat ma'lumotlarini ko'rish uchun ruxsat berilmagan");
                }
            }
            params.setRegionId(office.getRegionId());
        } else if (user.getRole() == Role.LEGAL) {
            //TODO: legal va individual uchun yozish kerak
        }


        return equipmentRepository.getAllByParams(user,params);
    }
    @Override
    public Page<HfPageView> getAllAttractionForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        UUID profileId = user.getProfileId();
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", profileId));
        Integer regionId = profile.getRegionId();
        if (isAssigned) {
            if (tin != null) return equipmentRepository.getAllByLegalTinAndInterval(pageable, tin, intervalId, EquipmentType.ATTRACTION);
            if (registryNumber != null) return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ATTRACTION);
            else return equipmentRepository.getAllByRegionAndInterval(pageable, regionId, intervalId, EquipmentType.ATTRACTION);
        } else {
            if (tin != null) return equipmentRepository.getAllByLegalTin(pageable, tin, EquipmentType.ATTRACTION);
            if (registryNumber != null) return equipmentRepository.getAllByRegistryNumber(pageable, registryNumber, EquipmentType.ATTRACTION);
            else return equipmentRepository.getAllByRegion(pageable, regionId, EquipmentType.ATTRACTION);
        }
    }

    @Override
    public EquipmentViewById getById(UUID equipmentId) {
        Equipment equipment = equipmentRepository.getEquipmentById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Equipment", "ID", equipmentId));
        return mapToView(equipment);
    }

    private EquipmentViewById mapToView(Equipment equipment) {
        return new EquipmentViewById(
                equipment.getRegistrationDate(),
                equipment.getType(),
                equipment.getAppealId(),
                equipment.getRegistryNumber(),
                equipment.getLegalTin(),
                equipment.getHazardousFacilityId(),
                equipment.getHazardousFacility() == null? null : equipment.getHazardousFacility().getName(),
                equipment.getChildEquipmentId(),
                equipment.getChildEquipment() == null? null : equipment.getChildEquipment().getName(),
                equipment.getFactoryNumber(),
                equipment.getAddress(),
                equipment.getModel(),
                equipment.getFactory(),
                equipment.getLocation(),
                equipment.getManufacturedAt(),
                equipment.getOldEquipmentId(),
                equipment.getOldEquipment() == null? null : equipment.getOldEquipment().getRegistryNumber(),
                equipment.getParameters(),
                equipment.getSphere(),
                equipment.getAttractionName(),
                equipment.getAcceptedAt(),
                equipment.getChildEquipmentSortId(),
                equipment.getChildEquipmentSort() == null? null : equipment.getChildEquipmentSort().getName(),
                equipment.getCountry(),
                equipment.getServicePeriod(),
                equipment.getRiskLevel(),
                equipment.getParentOrganization(),
                equipment.getNonDestructiveCheckDate(),
                equipment.getAttractionPassportId(),
                equipment.getDescription(),
                equipment.getInspectorId(),
                equipment.getInspector() == null? null : equipment.getInspector().getName(),
                equipment.getFiles(),
                equipment.getRegistryFilePath());
    }


    @Override
    public Page<HfPageView> getAllElevatorForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        UUID profileId = user.getProfileId();
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", profileId));
        Integer regionId = profile.getRegionId();
        if (isAssigned) {
            if (tin != null) return equipmentRepository.getAllByLegalTinAndInterval(pageable, tin, intervalId, EquipmentType.ELEVATOR);
            if (registryNumber != null) return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ELEVATOR);
            else return equipmentRepository.getAllByRegionAndInterval(pageable, regionId, intervalId, EquipmentType.ELEVATOR);
        } else {
            if (tin != null) return equipmentRepository.getAllByLegalTin(pageable, tin, EquipmentType.ELEVATOR);
            if (registryNumber != null) return equipmentRepository.getAllByRegistryNumber(pageable, registryNumber, EquipmentType.ELEVATOR);
            else return equipmentRepository.getAllByRegion(pageable, regionId, EquipmentType.ELEVATOR);
        }
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

//    private String createAttractionPassportPdf(EquipmentDto dto, String legalAddress) {
//        Map<String, String> parameters = new HashMap<>();
//
//        parameters.put("attractionName", dto.attractionName());
//        parameters.put("attractionType", childEquipmentService.getById(dto.childEquipmentId()).getName());
//        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
//        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
//        parameters.put("legalName", dto.legalName());
//        parameters.put("legalTin", dto.legalTin().toString());
//        parameters.put("legalAddress", legalAddress);
//        parameters.put("registryNumber", dto.number());
//        parameters.put("factoryNumber", dto.factoryNumber());
//        parameters.put("factory", dto.factory());
//        parameters.put("regionName", dto.regionName());
//        parameters.put("districtName", districtService.getDistrict(dto.districtId()).getName());
//        parameters.put("address", dto.address());
//        parameters.put("riskLevel", dto.riskLevel().value);
//
//        String content = getTemplateContent(TemplateType.REGISTRY_ATTRACTION);
//
//        return attachmentService.createPdfFromHtml(content, "reestr/attraction", parameters, false);
//    }

//    private String createEquipmentPdf(EquipmentDto dto, String legalAddress) {
//        Map<String, String> parameters = new HashMap<>();
//
//        parameters.put("legalAddress", legalAddress);
//        parameters.put("equipmentType", childEquipmentService.getById(dto.childEquipmentId()).getName());
//        parameters.put("childEquipmentSortName", childEquipmentSortService.getById(dto.childEquipmentSortId()).getName());
//        parameters.put("legalTin", dto.legalTin().toString());
//        parameters.put("factoryNumber", dto.factoryNumber());
//        parameters.put("factory", dto.factory());
//        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
//        parameters.put("number", dto.number());
//        parameters.put("registrationDate", LocalDate.now().toString());
//        parameters.put("address", dto.address());
//        parameters.put("parameters", "PARAMETERS"); // TODO
//
//        String content = getTemplateContent(TemplateType.REGISTRY_EQUIPMENT);
//
//        return attachmentService.createPdfFromHtml(content, "reestr/equipment", parameters, false);
//    }

    private String getTemplateContent(TemplateType type) {
        return templateService.getByType(type.name()).getContent();
    }
}
