package uz.technocorp.ecosystem.modules.hf;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.hf.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;
import uz.technocorp.ecosystem.modules.hf.view.HfViewById;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
    private final RegionService regionService;
    private final DistrictService districtService;
    private final AttachmentService attachmentService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    @Override
    public void create(Appeal appeal) {
        Long maxOrderNumber = repository.findMaxOrderNumber().orElse(0L) + 1;

        Region region = regionService.getById(appeal.getRegionId());
        District district = districtService.getDistrict(appeal.getDistrictId());

        String registryNumber = String.format("%05d", maxOrderNumber) + "-" + String.format("%04d", district.getNumber()) + "-" + String.format("%02d", region.getNumber());
        HfAppealDto hfAppealDto = JsonParser.parseJsonData(appeal.getData(), HfAppealDto.class);

        // Make parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("upperOrganization", hfAppealDto.getUpperOrganization());
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("name", hfAppealDto.getName());
        parameters.put("region", region.getName());
        parameters.put("district", district.getName());
        parameters.put("address", hfAppealDto.getAddress());
        parameters.put("hfTypeName", hfAppealDto.getHfTypeName());
        parameters.put("registrationDate", LocalDate.now().toString());
        parameters.put("number", appeal.getNumber());
        parameters.put("extraArea", hfAppealDto.getExtraArea());
        parameters.put("hazardousSubstance", hfAppealDto.getHazardousSubstance());

        // Find template
        Template template = templateService.getByType(TemplateType.REGISTRY_HF.name());

        // Create file
        String registryFilePath = attachmentService.createPdfFromHtml(template.getContent(), "reestr/hf", parameters, false);

        repository.save(
                HazardousFacility.builder()
                        .legalTin(appeal.getLegalTin())
                        .legalName(appeal.getLegalName())
                        .regionId(appeal.getRegionId())
                        .districtId(appeal.getDistrictId())
                        .orderNumber(maxOrderNumber)
                        .profileId(appeal.getProfileId())
                        .legalAddress(appeal.getLegalAddress())
                        .phoneNumber(appeal.getPhoneNumber())
                        .upperOrganization(hfAppealDto.getUpperOrganization())
                        .name(hfAppealDto.getName())
                        .address(appeal.getAddress())
                        .location(hfAppealDto.getLocation())
                        .hazardousSubstance(hfAppealDto.getHazardousSubstance())
                        .appealId(appeal.getId())
                        .hfTypeId(hfAppealDto.getHfTypeId())
                        .extraArea(hfAppealDto.getExtraArea())
                        .registryNumber(registryNumber)
                        .registrationDate(LocalDate.now())
                        .active(true)
                        .spheres(hfAppealDto.getSpheres())
                        .files(hfAppealDto.getFiles())
                        .registryFilePath(registryFilePath)
                        .build());
    }

//    @Override
//    public void create(HfDto dto) {
//
//        Region region = regionRepository
//                .findById(dto.regionId())
//                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", dto.regionId()));
//
//        District district = districtRepository
//                .findById(dto.districtId())
//                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.districtId()));
//
//        Profile profile = profileRepository.findByTin(dto.legalTin()).orElseThrow(() -> new ResourceNotFoundException("Profile", "Tin", dto.legalTin()));
//
//        repository.save(
//                HazardousFacility.builder()
//                        .legalTin(dto.legalTin())
//                        .legalName(profile.getLegalName())
//                        .regionId(dto.regionId())
//                        .districtId(dto.districtId())
//                        .profileId(profile.getId())
//                        .legalAddress(profile.getLegalAddress())
//                        .phoneNumber(dto.phoneNumber())
//                        .email(dto.email())
//                        .upperOrganization(dto.upperOrganization())
//                        .name(dto.name())
//                        .address(region.getName()+", "+district.getName()+", "+dto.address())
//                        .location(dto.location())
//                        .hazardousSubstance(dto.hazardousSubstance())
//                        .spheres(dto.spheres())
//                        .registrationDate(dto.registrationDate())
//                        .hfTypeId(dto.hfTypeId())
//                        .extraArea(dto.extraArea())
//                        .description(dto.description())
//                        .registryNumber(dto.registryNumber())
//                        .registrationDate(dto.registrationDate())
//                        .active(true)
//                        .appointmentOrderPath(dto.appointmentOrderPath())
//                        .cadastralPassportPath(dto.cadastralPassportPath())
//                        .certificationPath(dto.certificationPath())
//                        .permitPath(dto.permitPath())
//                        .deviceTestingPath(dto.deviceTestingPath())
//                        .licensePath(dto.licensePath())
//                        .ecologicalConclusionPath(dto.ecologicalConclusionPath())
//                        .expertOpinionPath(dto.expertOpinionPath())
//                        .industrialSafetyDeclarationPath(dto.industrialSafetyDeclarationPath())
//                        .insurancePolicyPath(dto.insurancePolicyPath())
//                        .projectDocumentationPath(dto.projectDocumentationPath())
//                        .replyLetterPath(dto.replyLetterPath())
//                        .identificationCardPath(dto.identificationCardPath())
//                        .receiptPath(dto.receiptPath())
//                        .build());
//    }

    @Override
    public void update(UUID id, HfDto dto) {
        HazardousFacility hazardousFacility = findById(id);

        Profile profile = profileService.findByTin(dto.legalTin());

        hazardousFacility.setLegalTin(dto.legalTin());
        hazardousFacility.setLegalName(profile.getLegalName());
        hazardousFacility.setRegionId(dto.regionId());
        hazardousFacility.setDistrictId(dto.districtId());
        hazardousFacility.setLegalAddress(profile.getLegalAddress());
        hazardousFacility.setPhoneNumber(dto.phoneNumber());
        hazardousFacility.setUpperOrganization(dto.upperOrganization());
        hazardousFacility.setName(dto.name());
        hazardousFacility.setAddress(dto.address());
        hazardousFacility.setLocation(dto.location());
        hazardousFacility.setHazardousSubstance(dto.hazardousSubstance());
        hazardousFacility.setHfTypeId(dto.hfTypeId());
        hazardousFacility.setExtraArea(dto.extraArea());
        hazardousFacility.setDescription(dto.description());
        hazardousFacility.setRegistryNumber(dto.registryNumber());
        hazardousFacility.setSpheres(dto.spheres());
        hazardousFacility.setRegistrationDate(dto.registrationDate());
        //TODO HfDtoga filesni set qilishni yozish kerak
//        hazardousFacility.setAppointmentOrderPath(dto.appointmentOrderPath());
//        hazardousFacility.setCadastralPassportPath(dto.cadastralPassportPath());
//        hazardousFacility.setCertificationPath(dto.certificationPath());
//        hazardousFacility.setPermitPath(dto.permitPath());
//        hazardousFacility.setIndustrialSafetyDeclarationPath(dto.industrialSafetyDeclarationPath());
//        hazardousFacility.setLicensePath(dto.licensePath());
//        hazardousFacility.setEcologicalConclusionPath(dto.ecologicalConclusionPath());
//        hazardousFacility.setExpertOpinionPath(dto.expertOpinionPath());
//        hazardousFacility.setInsurancePolicyPath(dto.insurancePolicyPath());
//        hazardousFacility.setProjectDocumentationPath(dto.projectDocumentationPath());
//        hazardousFacility.setIdentificationCardPath(dto.identificationCardPath());
//        hazardousFacility.setDeviceTestingPath(dto.deviceTestingPath());
//        hazardousFacility.setReceiptPath(dto.receiptPath());
        repository.save(hazardousFacility);
    }

    @Override
    public void deregister(UUID id, HfDeregisterDto dto) {
        HazardousFacility hazardousFacility = findById(id);
        hazardousFacility.setActive(false);
        hazardousFacility.setDeregisterFilePath(dto.deregisterFilePath());
        hazardousFacility.setDeregisterReason(dto.deregisterReason());
        hazardousFacility.setRegistryNumber(
                hazardousFacility.getRegistryNumber() + "/р-ч" + String.format("%05d", hazardousFacility.getOrderNumber())
        );

        repository.save(hazardousFacility);
    }

    @Override
    public void periodicUpdate(UUID id, HfPeriodicUpdateDto dto) {
        HazardousFacility hazardousFacility = findById(id);
        hazardousFacility.setPeriodicUpdateFilePath(dto.periodicUpdateFilePath());
        hazardousFacility.setPeriodicUpdateReason(dto.periodicUpdateReason());
        hazardousFacility.setRegistryNumber(
                hazardousFacility.getRegistryNumber() + "/д-я" + String.format("%05d", hazardousFacility.getOrderNumber())
        );

        repository.save(hazardousFacility);
    }

    @Override
    public List<HfSelectView> findAllByProfile(User user) {
        return repository.findAllByProfileId(user.getProfileId());
    }

    @Override
    public Page<HfCustom> getAll(User user, HfParams params) {
        return repository.getHfCustoms(user, params);
    }

    @Override
    public String getHfNameById(UUID hfId) {
        return findById(hfId).getName();
    }

    @Override
    public Page<HfPageView> getAllForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        UUID profileId = user.getProfileId();
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", profileId));
        Integer regionId = profile.getRegionId();
        if (isAssigned) {
            if (tin != null) return repository.getAllByLegalTinAndInterval(pageable, tin, intervalId);
            if (registryNumber != null)
                return repository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId);
            else return repository.getAllByRegionAndInterval(pageable, regionId, intervalId);
        } else {
            if (tin != null) return repository.getAllByLegalTin(pageable, tin);
            if (registryNumber != null) return repository.getAllByRegistryNumber(pageable, registryNumber);
            else return repository.getAllByRegion(pageable, regionId);
        }
    }

    @Override
    public HfViewById getById(UUID hfId) {
        HazardousFacility hf = repository.getHfById(hfId).orElseThrow(() -> new ResourceNotFoundException("Xicho", "ID", hfId));
        return mapToView(hf);
    }

    private HfViewById mapToView(HazardousFacility hf) {
        return new HfViewById(
                hf.getLegalTin(),
                hf.getRegistrationDate(),
                hf.getRegistryNumber(),
                hf.getProfileId(),
                hf.getUpperOrganization(),
                hf.getName(),
                hf.getAddress(),
                hf.getLocation(),
                hf.getHazardousSubstance(),
                hf.getAppealId(),
                hf.getHfTypeId(),
                hf.getHfType() == null ? null : hf.getHfType().getName(),
                hf.getExtraArea(),
                hf.getDescription(),
                hf.getSpheres(),
                hf.getDeregisterReason(),
                hf.getDeregisterFilePath(),
                hf.getPeriodicUpdateReason(),
                hf.getPeriodicUpdateFilePath(),
                hf.isActive(),
                hf.getFiles());
    }

    public HazardousFacility findById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "ID", id));
    }
}
