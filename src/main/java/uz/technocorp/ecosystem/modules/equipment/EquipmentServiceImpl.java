package uz.technocorp.ecosystem.modules.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.dto.*;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentParameter;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.*;
import uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto.DeregisterEquipmentDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto.ReRegisterEquipmentDto;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

    private final AttachmentService attachmentService;
    private final TemplateService templateService;
    private final DistrictService districtService;
    private final EquipmentRepository repository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final OfficeService officeService;
    private final ChildEquipmentService childEquipmentService;

    @Override
    public void create(Appeal appeal) {
        Profile profile = profileService.findByIdentity(appeal.getOwnerIdentity());

        EquipmentDto dto = JsonParser.parseJsonData(appeal.getData(), EquipmentDto.class);
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType(), appeal.getMode());

        EquipmentRegistryDto registryDto = new EquipmentRegistryDto();

        registryDto.setType(info.equipmentType());
        registryDto.setRegistryNumber(info.registryNumber());
        registryDto.setRegistrationDate(LocalDate.now());
        registryDto.setManufacturedAt(dto.manufacturedAt());
        registryDto.setFactory(dto.factory());
        registryDto.setFactoryNumber(dto.factoryNumber());
        registryDto.setModel(dto.model());
        registryDto.setParameters(dto.parameters());
        registryDto.setAttractionName(dto.attractionName());
        registryDto.setRiskLevel(dto.riskLevel());

        // Create registry PDF
        String registryFilepath = createEquipmentRegistryPdf(appeal, registryDto);

        Equipment equipment = Equipment.builder()
                .type(info.equipmentType())
                .appealId(appeal.getId())
                .registryNumber(info.registryNumber())
                .orderNumber(info.orderNumber())
                .ownerIdentity(appeal.getOwnerIdentity())
                .ownerName(profile.getName())
                .ownerType(OwnerType.find(appeal.getOwnerIdentity().toString().length()))
                .hazardousFacilityId(dto.hazardousFacilityId())
                .childEquipmentId(dto.childEquipmentId())
                .factoryNumber(dto.factoryNumber())
                .regionId(appeal.getRegionId())
                .districtId(appeal.getDistrictId())
                .ownerAddress(appeal.getOwnerAddress())
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
                .isActive(true)
                .mode(appeal.getMode())
                .build();

        repository.save(equipment);
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
        } else if (user.getRole().equals(Role.LEGAL) || user.getRole().equals(Role.INDIVIDUAL)) {
            params.setSearch(profile.getIdentity().toString());
        } else {
            //TODO zaruriyat bo'lsa boshqa rollar uchun logika yozish kerak
        }

        return repository.getAllByParams(user, params);
    }

    @Override
    public EquipmentViewById getById(UUID equipmentId) {
        Equipment equipment = repository.getEquipmentById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Equipment", "ID", equipmentId));
        return mapToView(equipment);
    }

    @Override
    public Equipment findById(UUID equipmentId) {
        return repository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Equipment", "ID", equipmentId));
    }

    @Override
    public AttractionPassportView getAttractionPassportByRegistryNumber(String registryNumber) {
        Equipment equipment = repository.findFetchedEquipmentByRegistryNumber(registryNumber).orElse(null);
        if (equipment == null) return null;
        return mapToAttractionPassportView(equipment);
    }

    @Override
    public EquipmentViewById findByRegistryNumber(String registryNumber) {
        return repository.findByRegistryNumber(registryNumber).map(this::mapToView).orElseThrow(() -> new ResourceNotFoundException("Qurilma", "registratsiya", registryNumber));
    }

    @Override
    public Long getCount(User user) {
        Profile profile = profileService.getProfile(user.getProfileId());
        return switch (user.getRole()) {
            case LEGAL, INDIVIDUAL -> repository.countByParams(profile.getIdentity(), null);
            case REGIONAL, INSPECTOR -> repository.countByParams(null, profile.getRegionId());
            default -> repository.countByParams(null, null);
        };
    }

    @Override
    public Page<EquipmentRiskView> getAllEquipmentRiskAssessment(EquipmentRiskParamsDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        Role role = dto.getUser().getRole();

        if (role == Role.REGIONAL) {
            Profile profile = profileService.getProfile(dto.getUser().getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            Integer regionId = office.getRegionId();
            if (dto.getIsAssigned()) {
                if (dto.getRegistryNumber() != null)
                    return repository.getAllByRegistryNumberAndInterval(pageable, dto.getRegistryNumber(), dto.getIntervalId(), dto.getEquipmentType().name());
                if (dto.getLegalTin() != null)
                    return repository.getAllByLegalTinAndInterval(pageable, dto.getLegalTin(), dto.getIntervalId(), dto.getEquipmentType().name());
                else
                    return repository.getAllByRegionAndInterval(pageable, regionId, dto.getIntervalId(), dto.getEquipmentType().name());
            } else {
                if (dto.getLegalTin() != null)
                    return repository.getAllByLegalTin(pageable, dto.getLegalTin(), dto.getEquipmentType().name(), dto.getIntervalId());
                if (dto.getRegistryNumber() != null)
                    return repository.getAllByRegistryNumber(pageable, dto.getRegistryNumber(), dto.getEquipmentType().name(), dto.getIntervalId());
                else
                    return repository.getAllByRegion(pageable, regionId, dto.getEquipmentType().name(), dto.getIntervalId());
            }

        } else if (role == Role.INSPECTOR) {
            if (dto.getRegistryNumber() != null)
                return repository.getAllByRegistryNumberAndIntervalAndInspectorId(pageable, dto.getRegistryNumber(), dto.getIntervalId(), dto.getEquipmentType().name(), dto.getUser().getId());
            if (dto.getLegalTin() != null)
                return repository
                        .getAllByLegalTinAndIntervalAndInspectorId(pageable, dto.getLegalTin(), dto.getIntervalId(), dto.getEquipmentType().name(), dto.getUser().getId());
            else
                return repository.getAllByInspectorIdAndInterval(pageable, dto.getUser().getId(), dto.getIntervalId(), dto.getEquipmentType().name());

        } else if (role == Role.CHAIRMAN || role == Role.MANAGER) {
            if (dto.getRegistryNumber() != null)
                return repository.getAllByRegistryNumberAndIntervalAndOwnerType(pageable, dto.getRegistryNumber(), dto.getIntervalId(), dto.getEquipmentType().name(), OwnerType.LEGAL.name());
            if (dto.getLegalTin() != null)
                return repository
                        .getAllByLegalTinAndIntervalAndOwnerType(pageable, dto.getLegalTin(), dto.getIntervalId(), dto.getEquipmentType().name(), OwnerType.LEGAL.name());
            else
                return repository.getAllByIntervalAndOwnerType(pageable, dto.getIntervalId(), dto.getEquipmentType().name(), OwnerType.LEGAL.name());

        } else {
            if (dto.getRegistryNumber() != null)
                return repository.getAllByRegistryNumberAndInterval(pageable, dto.getRegistryNumber(), dto.getIntervalId(), dto.getEquipmentType().name());
            Profile profile = profileService.getProfile(dto.getUser().getProfileId());
            return repository.getAllByLegalTinAndInterval(pageable, profile.getIdentity(), dto.getIntervalId(), dto.getEquipmentType().name());
        }
    }

    @Override
    public String createEquipmentRegistryPdf(Appeal appeal, EquipmentRegistryDto dto) {
        // Create registry PDF with parameters
        return EquipmentType.ATTRACTION_PASSPORT.equals(dto.getType())
                ? createAttractionPassportPdf(appeal, dto) // Attraction Passport
                : createEquipmentPdf(appeal, dto); // All Equipments
    }

    @Override
    public List<Equipment> getAllEquipmentByTypeAndTinOrPin(Long tin, EquipmentType type) {
        return repository.findAllByOwnerIdentityAndType(tin, type);
    }

    @Override
    public Equipment findByRegistryNumberAndTypeAndActive(String registryNumber, EquipmentType type, Boolean isActive) {
        return repository.findByRegistryNumberAndTypeAndIsActive(registryNumber, type, isActive).orElseThrow(() -> new ResourceNotFoundException("Qurilma", "registratsiya", registryNumber));
    }

    @Override
    public Equipment findByRegistryNumberAndTypeAndOwnerAndActive(String registryNumber, Long ownerIdentity, EquipmentType type, Boolean active) {
        return repository.findByRegistryNumberAndOwnerIdentityAndTypeAndIsActive(registryNumber, ownerIdentity, type, active)
                .orElseThrow(() -> new CustomException("Sizga tegishli aktiv qurilma topilmadi"));
    }

    @Override
    public void deactivateEquipment(Appeal appeal) {
        DeregisterEquipmentDto dto = JsonParser.parseJsonData(appeal.getData(), DeregisterEquipmentDto.class);
        repository.deactivateByRegistryNumber(dto.getRegistryNumber(), LocalDate.now());
    }

    @Override
    public void reRegister(Appeal appeal) {
        ReRegisterEquipmentDto dto = JsonParser.parseJsonData(appeal.getData(), ReRegisterEquipmentDto.class);

        Equipment old = findByRegistryNumberAndTypeAndActive(dto.getOldRegistryNumber(), dto.getType(), false);
        EquipmentInfoDto info = getEquipmentInfoByAppealType(appeal.getAppealType(), appeal.getMode());

        EquipmentRegistryDto registryDto = new EquipmentRegistryDto();
        registryDto.setType(info.equipmentType());
        registryDto.setRegistryNumber(info.registryNumber());
        registryDto.setRegistrationDate(LocalDate.now());
        registryDto.setManufacturedAt(old.getManufacturedAt());
        registryDto.setFactory(old.getFactory());
        registryDto.setFactoryNumber(old.getFactoryNumber());
        registryDto.setModel(old.getModel());
        registryDto.setParameters(old.getParameters());
        registryDto.setAttractionName(old.getAttractionName());
        registryDto.setRiskLevel(old.getRiskLevel());

        // Create registry PDF
        String registryFilepath = createEquipmentRegistryPdf(appeal, registryDto);

        Equipment equipment = Equipment.builder()
                .type(old.getType())
                .appealId(appeal.getId())
                .registryNumber(info.registryNumber())
                .oldRegistryNumber(old.getRegistryNumber())
                .orderNumber(info.orderNumber())
                .ownerIdentity(appeal.getOwnerIdentity())
                .ownerName(appeal.getOwnerName())
                .ownerType(OwnerType.find(appeal.getOwnerIdentity().toString().length()))
                .hazardousFacilityId(dto.getHazardousFacilityId())
                .childEquipmentId(old.getChildEquipmentId())
                .factoryNumber(old.getFactoryNumber())
                .regionId(appeal.getRegionId())
                .districtId(appeal.getDistrictId())
                .ownerAddress(appeal.getOwnerAddress())
                .address(appeal.getAddress())
                .model(old.getModel())
                .factory(old.getFactory())
                .location(dto.getLocation())
                .manufacturedAt(old.getManufacturedAt())
                .partialCheckDate(old.getPartialCheckDate())
                .fullCheckDate(old.getFullCheckDate())
                .parameters(old.getParameters())
                .sphere(old.getSphere())
                .attractionName(old.getAttractionName())
                .acceptedAt(old.getAcceptedAt())
                .childEquipmentSortId(old.getChildEquipmentSortId())
                .country(old.getCountry())
                .servicePeriod(old.getServicePeriod())
                .riskLevel(old.getRiskLevel())
                .parentOrganization(old.getParentOrganization())
                .nonDestructiveCheckDate(old.getNonDestructiveCheckDate())
                .files(dto.getFiles())
                .description(old.getDescription())
                .inspectorName(appeal.getExecutorName())
                .registryFilePath(registryFilepath)
                .registrationDate(LocalDate.now())
                .attractionPassportId(old.getAttractionPassportId())
                .isActive(true)
                .mode(appeal.getMode())
                .build();

        repository.save(equipment);
    }

    @Override
    public EquipmentCountByStatusView countEquipmentStatusByDateAndRegionId(LocalDate date, Integer regionId) {
        return repository.countStatusByPeriodAndRegionId(date, regionId);
    }

    // HELPER
    protected EquipmentInfoDto getEquipmentInfoByAppealType(AppealType appealType, RegistrationMode mode) {
        return switch (appealType) {
            case REGISTER_CRANE, RE_REGISTER_CRANE -> getInfo(EquipmentType.CRANE, "P", mode);
            case REGISTER_CONTAINER, RE_REGISTER_CONTAINER -> getInfo(EquipmentType.CONTAINER, "A", mode);
            case REGISTER_BOILER, RE_REGISTER_BOILER -> getInfo(EquipmentType.BOILER, "K", mode);
            case REGISTER_ELEVATOR, RE_REGISTER_ELEVATOR -> getInfo(EquipmentType.ELEVATOR, "L", mode);
            case REGISTER_ESCALATOR, RE_REGISTER_ESCALATOR -> getInfo(EquipmentType.ESCALATOR, "E", mode);
            case REGISTER_CABLEWAY, RE_REGISTER_CABLEWAY -> getInfo(EquipmentType.CABLEWAY, "KD", mode);
            case REGISTER_HOIST, RE_REGISTER_HOIST -> getInfo(EquipmentType.HOIST, "V", mode);
            case REGISTER_PIPELINE, RE_REGISTER_PIPELINE -> getInfo(EquipmentType.PIPELINE, "T", mode);
            case REGISTER_ATTRACTION_PASSPORT, RE_REGISTER_ATTRACTION_PASSPORT ->
                    getInfo(EquipmentType.ATTRACTION_PASSPORT, "AT", mode);
            case REGISTER_ATTRACTION, RE_REGISTER_ATTRACTION -> getInfo(EquipmentType.ATTRACTION, "ADR", mode);
            case REGISTER_CHEMICAL_CONTAINER, RE_REGISTER_CHEMICAL_CONTAINER ->
                    getInfo(EquipmentType.CHEMICAL_CONTAINER, "XA", mode);
            case REGISTER_HEAT_PIPELINE, RE_REGISTER_HEAT_PIPELINE -> getInfo(EquipmentType.HEAT_PIPELINE, "PAX", mode);
            case REGISTER_BOILER_UTILIZER, RE_REGISTER_BOILER_UTILIZER -> getInfo(EquipmentType.BOILER_UTILIZER, "KC", mode);
            case REGISTER_LPG_CONTAINER, RE_REGISTER_LPG_CONTAINER -> getInfo(EquipmentType.LPG_CONTAINER, "AG", mode);
            case REGISTER_LPG_POWERED, RE_REGISTER_LPG_POWERED -> getInfo(EquipmentType.LPG_POWERED, "TG", mode);
            default -> throw new RuntimeException("Ariza turi hech bir qurilma turiga mos kelmadi");
        };
    }

    private EquipmentInfoDto getInfo(EquipmentType equipmentType, String label, RegistrationMode mode) {
        long orderNumber = repository.getMax(equipmentType).orElse(0L) + 1;
        String formatted = String.format("%06d%s", orderNumber, "S" + (RegistrationMode.OFFICIAL.equals(mode)? "" : "/nr")); // 'S' means that the number was given by system. '/nr' means that it is 'norasmiy'
        return new EquipmentInfoDto(equipmentType, label + formatted, orderNumber);
    }

    private String createAttractionPassportPdf(Appeal appeal, EquipmentRegistryDto dto) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("attractionName", dto.getAttractionName());
        parameters.put("attractionType", appeal.getData().get("childEquipmentName").asText());
        parameters.put("childEquipmentSortName", appeal.getData().get("childEquipmentSortName").asText());
        parameters.put("manufacturedAt", dto.getManufacturedAt().toString());
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("legalTin", appeal.getOwnerIdentity().toString());
        parameters.put("registryNumber", dto.getRegistryNumber());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("regionName", regionService.findById(appeal.getRegionId()).getName());
        parameters.put("districtName", districtService.findById(appeal.getDistrictId()).getName());
        parameters.put("address", appeal.getAddress());
        parameters.put("riskLevel", dto.getRiskLevel().value);
        parameters.put("registrationDate", dto.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if (appeal.getOwnerAddress() == null || "null".equals(appeal.getOwnerAddress())) {
            parameters.put("legalAddress", "-");
        } else {
            parameters.put("legalAddress", appeal.getOwnerAddress());
        }

        String content = getTemplateContent(TemplateType.REGISTRY_ATTRACTION);

        return attachmentService.createPdfFromHtml(content, "reestr/attraction-passport", parameters, false);
    }

    private String createEquipmentPdf(Appeal appeal, EquipmentRegistryDto dto) {
        Map<String, String> parameters = new HashMap<>();

        if (appeal.getOwnerAddress() == null || "null".equals(appeal.getOwnerAddress())) {
            parameters.put("legalAddress", "-");
        } else {
            parameters.put("legalAddress", appeal.getOwnerAddress());
        }

        parameters.put("equipmentType", dto.getType().value);
        parameters.put("childEquipmentName", appeal.getData().get("childEquipmentName").asText());
        parameters.put("legalTin", appeal.getOwnerIdentity().toString());
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("factory", dto.getFactory());
        parameters.put("model", dto.getModel());
        parameters.put("manufacturedAt", dto.getManufacturedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        parameters.put("number", dto.getRegistryNumber());
        parameters.put("registrationDate", dto.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        parameters.put("address", appeal.getAddress());
        parameters.put("dynamicParameters", makeDynamicRows(dto.getParameters()));

        // get template by registration mode
        String content = getTemplateContent(
                RegistrationMode.OFFICIAL.equals(appeal.getMode())
                ? TemplateType.REGISTRY_EQUIPMENT
                : TemplateType.UNOFFICIAL_REGISTRY_EQUIPMENT);

        return attachmentService.createPdfFromHtml(
                content,
                RegistrationMode.OFFICIAL.equals(appeal.getMode()) ? "reestr/equipment" :"reestr/equipment/unofficial",
                parameters,
                false);
    }

    private String makeDynamicRows(Map<String, String> parameters) {
        StringBuilder dynamicRows = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            dynamicRows.append("<div class=\"list-item\">")
                    .append("<div class=\"bold\">").append(EquipmentParameter.nameWithUnit(entry.getKey())).append(":").append("</div>")
                    .append("<div>").append(entry.getValue()).append("</div>")
                    .append("</div>");
        }
        return dynamicRows.toString();
    }

    private String getTemplateContent(TemplateType type) {
        return templateService.getByType(type.name()).getContent();
    }

    // MAPPER
    private EquipmentViewById mapToView(Equipment equipment) {
        return new EquipmentViewById(
                equipment.getRegistrationDate(),
                equipment.getType(),
                equipment.getAppealId(),
                equipment.getRegistryNumber(),
                equipment.getOwnerIdentity(),
                equipment.getOwnerType() == null ? OwnerType.LEGAL.name() : equipment.getOwnerType().name(),
                equipment.getHazardousFacilityId(),
                equipment.getHazardousFacility() == null ? null : equipment.getHazardousFacility().getName(),
                equipment.getChildEquipmentId(),
                childEquipmentService.getNameById(equipment.getChildEquipmentId()),
                equipment.getFactoryNumber(),
                equipment.getAddress(),
                equipment.getModel(),
                equipment.getFactory(),
                equipment.getLocation(),
                equipment.getManufacturedAt(),
                equipment.getOldRegistryNumber(),
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
}
