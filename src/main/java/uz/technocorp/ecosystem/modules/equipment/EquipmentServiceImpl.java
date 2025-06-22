package uz.technocorp.ecosystem.modules.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentParameter;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.AttractionPassportView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentViewById;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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

    private final RegionService regionService;
    private final ProfileService profileService;
    private final TemplateService templateService;
    private final DistrictService districtService;
    private final AttachmentService attachmentService;
    private final OfficeService officeService;
    private final EquipmentRepository equipmentRepository;

    @Override
    public void create(Appeal appeal) {
        Profile profile = profileService.findByTin(appeal.getLegalTin());

        EquipmentDto dto = JsonParser.parseJsonData(appeal.getData(), EquipmentDto.class);
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType());

        // Create registry PDF
        String registryFilepath = createEquipmentRegistryPdf(appeal, dto, info);

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
                .inspectorName(appeal.getExecutorName())
                .registryFilePath(registryFilepath)
                .registrationDate(LocalDate.now())
                .attractionPassportId(dto.attractionPassportId())
                .legalAddress(appeal.getLegalAddress())
                .isActive(true)
                .build();

        equipmentRepository.save(equipment);
    }

    @Override
    public Page<EquipmentView> getAll(User user, EquipmentParams params) {

        Profile profile = profileService.getProfile(user.getProfileId());

        // check by role
        if (user.getRole() == Role.INSPECTOR || user.getRole() == Role.REGIONAL) {
            Office office = officeService.findById(profile.getOfficeId());
            if (params.getRegionId() != null) {
                if (!params.getRegionId().equals(office.getRegionId())) {
                    throw new RuntimeException("Sizga bu viloyat ma'lumotlarini ko'rish uchun ruxsat berilmagan");
                }
            }
            params.setRegionId(office.getRegionId());
        } else if (user.getRole() == Role.LEGAL) {
            params.setLegalTin(profile.getTin());
        } else {
            //TODO zaruriyat bo'lsa boshqa rollar uchun logika yozish kerak
        }

        return equipmentRepository.getAllByParams(user, params);
    }

    @Override
    public Page<HfPageView> getAllAttractionForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Role role = user.getRole();
        if (role == Role.REGIONAL) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            Integer regionId = office.getRegionId();
            if (isAssigned) {
                if (registryNumber != null)
                    return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ATTRACTION.name());
                if (tin != null)
                    return equipmentRepository.getAllByLegalTinAndInterval(pageable, tin, intervalId, EquipmentType.ATTRACTION.name());
                else
                    return equipmentRepository.getAllByRegionAndInterval(pageable, regionId, intervalId, EquipmentType.ATTRACTION.name());
            } else {
                if (tin != null)
                    return equipmentRepository.getAllByLegalTin(pageable, tin, EquipmentType.ATTRACTION.name(), intervalId);
                if (registryNumber != null)
                    return equipmentRepository.getAllByRegistryNumber(pageable, registryNumber, EquipmentType.ATTRACTION.name(), intervalId);
                else
                    return equipmentRepository.getAllByRegion(pageable, regionId, EquipmentType.ATTRACTION.name(), intervalId);
            }
        } else if (role == Role.INSPECTOR) {
            if (registryNumber != null)
                return equipmentRepository.getAllByRegistryNumberAndIntervalAndInspectorId(pageable, registryNumber, intervalId, EquipmentType.ATTRACTION.name(), user.getId());
            if (tin != null)
                return equipmentRepository
                        .getAllByLegalTinAndIntervalAndInspectorId(pageable, tin, intervalId, EquipmentType.ATTRACTION.name(), user.getId());
            else
                return equipmentRepository.getAllByInspectorIdAndInterval(pageable, user.getId(), intervalId, EquipmentType.ATTRACTION.name());

        } else {
            if (registryNumber != null)
                return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ATTRACTION.name());
            Profile profile = profileService.getProfile(user.getProfileId());
            return equipmentRepository.getAllByLegalTinAndInterval(pageable, profile.getTin(), intervalId, EquipmentType.ATTRACTION.name());
        }

    }

    @Override
    public EquipmentViewById getById(UUID equipmentId) {
        Equipment equipment = equipmentRepository.getEquipmentById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Equipment", "ID", equipmentId));
        return mapToView(equipment);
    }

    @Override
    public Equipment findById(UUID equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Equipment", "ID", equipmentId));
    }

    @Override
    public AttractionPassportView getAttractionPassportByRegistryNumber(String registryNumber) {
        Equipment equipment = equipmentRepository.findByRegistryNumber(registryNumber).orElse(null);
        if (equipment == null) return null;
        return mapToAttractionPassportView(equipment);
    }

    @Override
    public Equipment findByRegistryNumber(String oldEquipmentRegistryNumber) {
        return equipmentRepository.findByRegistryNumber(oldEquipmentRegistryNumber).orElseThrow(() -> new ResourceNotFoundException("Qurilma", "registratsiya", oldEquipmentRegistryNumber));
    }

    @Override
    public Long getCount(User user) {
        Profile profile = profileService.getProfile(user.getProfileId());
        return switch (user.getRole()) {
            case LEGAL -> equipmentRepository.countByParams(profile.getTin(), null);
            case REGIONAL, INSPECTOR -> equipmentRepository.countByParams(null, profile.getRegionId());
            default -> equipmentRepository.countByParams(null, null);
        };
    }

    private AttractionPassportView mapToAttractionPassportView(Equipment equipment) {
        return new AttractionPassportView(
                equipment.getId(),
                equipment.getAttractionName(),
                equipment.getChildEquipment() != null ? equipment.getChildEquipment().getName() : null,
                equipment.getChildEquipmentSort() != null ? equipment.getChildEquipmentSort().getName() : null,
                equipment.getManufacturedAt(),
                equipment.getAcceptedAt(),
                equipment.getFactoryNumber(),
                equipment.getCountry(),
                equipment.getRiskLevel());
    }

    @Override
    public Page<HfPageView> getAllElevatorForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Role role = user.getRole();
        if (role == Role.REGIONAL) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            Integer regionId = office.getRegionId();
            if (isAssigned) {
                if (registryNumber != null)
                    return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ELEVATOR.name());
                if (tin != null)
                    return equipmentRepository.getAllByLegalTinAndInterval(pageable, tin, intervalId, EquipmentType.ELEVATOR.name());
                else
                    return equipmentRepository.getAllByRegionAndInterval(pageable, regionId, intervalId, EquipmentType.ELEVATOR.name());
            } else {
                if (tin != null) return equipmentRepository
                        .getAllByLegalTin(pageable, tin, EquipmentType.ELEVATOR.name(), intervalId);
                if (registryNumber != null)
                    return equipmentRepository.getAllByRegistryNumber(pageable, registryNumber, EquipmentType.ELEVATOR.name(), intervalId);
                else
                    return equipmentRepository.getAllByRegion(pageable, regionId, EquipmentType.ELEVATOR.name(), intervalId);
            }
        } else if (role == Role.INSPECTOR) {
            if (registryNumber != null)
                return equipmentRepository.getAllByRegistryNumberAndIntervalAndInspectorId(pageable, registryNumber, intervalId, EquipmentType.ELEVATOR.name(), user.getId());
            if (tin != null)
                return equipmentRepository
                        .getAllByLegalTinAndIntervalAndInspectorId(pageable, tin, intervalId, EquipmentType.ELEVATOR.name(), user.getId());
            else
                return equipmentRepository.getAllByInspectorIdAndInterval(pageable, user.getId(), intervalId, EquipmentType.ELEVATOR.name());

        } else {
            if (registryNumber != null)
                return equipmentRepository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId, EquipmentType.ELEVATOR.name());
            Profile profile = profileService.getProfile(user.getProfileId());
            return equipmentRepository.getAllByLegalTinAndInterval(pageable, profile.getTin(), intervalId, EquipmentType.ELEVATOR.name());
        }
    }

    protected String createEquipmentRegistryPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info) {
        // Create registry PDF with parameters
        return EquipmentType.ATTRACTION_PASSPORT.equals(info.equipmentType())
                ? createAttractionPassportPdf(appeal, dto, info) // Attraction Passport
                : createEquipmentPdf(appeal, dto, info); // All Equipments
    }

    protected EquipmentInfoDto getEquipmentInfoByAppealType(AppealType appealType) {
        return switch (appealType) {
            case REGISTER_CRANE -> getInfo(EquipmentType.CRANE, "P");
            case REGISTER_CONTAINER -> getInfo(EquipmentType.CONTAINER, "AG");
            case REGISTER_BOILER -> getInfo(EquipmentType.BOILER, "K");
            case REGISTER_ELEVATOR -> getInfo(EquipmentType.ELEVATOR, "L");
            case REGISTER_ESCALATOR -> getInfo(EquipmentType.ESCALATOR, "E");
            case REGISTER_CABLEWAY -> getInfo(EquipmentType.CABLEWAY, "KD");
            case REGISTER_HOIST -> getInfo(EquipmentType.HOIST, "V");
            case REGISTER_PIPELINE -> getInfo(EquipmentType.PIPELINE, "T");
            case REGISTER_ATTRACTION_PASSPORT -> getInfo(EquipmentType.ATTRACTION_PASSPORT, "AT");
            case REGISTER_ATTRACTION -> getInfo(EquipmentType.ATTRACTION, "ADR");
            case REGISTER_CHEMICAL_CONTAINER -> getInfo(EquipmentType.CHEMICAL_CONTAINER, "XA");
            case REGISTER_HEAT_PIPELINE -> getInfo(EquipmentType.HEAT_PIPELINE, "PAX");
            case REGISTER_BOILER_UTILIZER -> getInfo(EquipmentType.BOILER_UTILIZER, "KC");
            case REGISTER_LPG_CONTAINER -> getInfo(EquipmentType.LPG_CONTAINER, "AG");
            case REGISTER_LPG_POWERED -> getInfo(EquipmentType.LPG_POWERED, "TG");
            default -> throw new RuntimeException("Ariza turi hech bir qurilma turiga mos kelmadi");
        };
    }

    private EquipmentInfoDto getInfo(EquipmentType equipmentType, String label) {
        long orderNumber = equipmentRepository.getMax(equipmentType).orElse(0L) + 1;
        return new EquipmentInfoDto(equipmentType, label + orderNumber, orderNumber);
    }

    private String createAttractionPassportPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("attractionName", dto.attractionName());
        parameters.put("attractionType", appeal.getData().get("childEquipmentName").asText());
        parameters.put("childEquipmentSortName", appeal.getData().get("childEquipmentSortName").asText());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("legalAddress", appeal.getLegalAddress());
        parameters.put("registryNumber", info.registryNumber());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("regionName", regionService.findById(appeal.getRegionId()).getName());
        parameters.put("districtName", districtService.findById(appeal.getDistrictId()).getName());
        parameters.put("address", appeal.getAddress());
        parameters.put("riskLevel", dto.riskLevel().value);

        String content = getTemplateContent(TemplateType.REGISTRY_ATTRACTION);

        return attachmentService.createPdfFromHtml(content, "reestr/attraction-passport", parameters, false);
    }

    private String createEquipmentPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("legalAddress", appeal.getLegalAddress());
        parameters.put("equipmentType", EquipmentType.valueOf(appeal.getData().get("type").asText()).value);
        parameters.put("childEquipmentName", appeal.getData().get("childEquipmentName").asText());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("factoryNumber", dto.factoryNumber());
        parameters.put("factory", dto.factory());
        parameters.put("model", dto.model());
        parameters.put("manufacturedAt", dto.manufacturedAt().toString());
        parameters.put("number", info.registryNumber());
        parameters.put("registrationDate", LocalDate.now().toString());
        parameters.put("address", appeal.getAddress());
        parameters.put("dynamicParameters", makeDynamicRows(dto.parameters()));

        String content = getTemplateContent(TemplateType.REGISTRY_EQUIPMENT);

        return attachmentService.createPdfFromHtml(content, "reestr/equipment", parameters, false);
    }

    private String makeDynamicRows(Map<String, String> parameters) {
        StringBuilder dynamicRows = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            dynamicRows.append("<div class=\"list-item\">")
                    .append("<div>").append(EquipmentParameter.nameWithUnit(entry.getKey())).append("</div>")
                    .append("<div>").append(entry.getValue()).append("</div>")
                    .append("</div>");
        }
        return dynamicRows.toString();
    }

    private String getTemplateContent(TemplateType type) {
        return templateService.getByType(type.name()).getContent();
    }

    private EquipmentViewById mapToView(Equipment equipment) {
        return new EquipmentViewById(
                equipment.getRegistrationDate(),
                equipment.getType(),
                equipment.getAppealId(),
                equipment.getRegistryNumber(),
                equipment.getLegalTin(),
                equipment.getHazardousFacilityId(),
                equipment.getHazardousFacility() == null ? null : equipment.getHazardousFacility().getName(),
                equipment.getChildEquipmentId(),
                equipment.getChildEquipment() == null ? null : equipment.getChildEquipment().getName(),
                equipment.getFactoryNumber(),
                equipment.getAddress(),
                equipment.getModel(),
                equipment.getFactory(),
                equipment.getLocation(),
                equipment.getManufacturedAt(),
                equipment.getOldEquipmentId(),
                equipment.getOldEquipment() == null ? null : equipment.getOldEquipment().getRegistryNumber(),
                equipment.getParameters(),
                equipment.getSphere(),
                equipment.getAttractionName(),
                equipment.getAcceptedAt(),
                equipment.getChildEquipmentSortId(),
                equipment.getChildEquipmentSort() == null ? null : equipment.getChildEquipmentSort().getName(),
                equipment.getCountry(),
                equipment.getServicePeriod(),
                equipment.getRiskLevel(),
                equipment.getParentOrganization(),
                equipment.getNonDestructiveCheckDate(),
                equipment.getAttractionPassportId(),
                equipment.getAttractionPassport() == null ? null : equipment.getAttractionPassport().getRegistryNumber(),
                equipment.getDescription(),
                equipment.getInspectorName(),
                equipment.getIsActive(),
                equipment.getFiles(),
                equipment.getRegistryFilePath());
    }
}
