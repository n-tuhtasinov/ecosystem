package uz.technocorp.ecosystem.modules.hf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.hf.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.List;
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
    private final ProfileRepository profileRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void create(Appeal appeal) {
        Long maxOrderNumber = repository.findMaxOrderNumber().orElse(0L) + 1;

        District district = districtRepository
                .findById(appeal.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", appeal.getDistrictId()));

        Region region = regionRepository
                .findById(appeal.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", appeal.getRegionId()));

        String registryNumber = String.format("%05d", maxOrderNumber) + "-" + String.format("%04d", district.getNumber()) + "-" + String.format("%02d", region.getNumber());
        HfAppealDto hfAppealDto = parseJsonData(appeal.getData());

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
                        .email(hfAppealDto.getEmail())
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
                        .appointmentOrderPath(hfAppealDto.getAppointmentOrderPath())
                        .cadastralPassportPath(hfAppealDto.getCadastralPassportPath())
                        .certificationPath(hfAppealDto.getCertificationPath())
                        .permitPath(hfAppealDto.getPermitPath())
                        .deviceTestingPath(hfAppealDto.getDeviceTestingPath())
                        .licensePath(hfAppealDto.getLicensePath())
                        .ecologicalConclusionPath(hfAppealDto.getEcologicalConclusionPath())
                        .expertOpinionPath(hfAppealDto.getExpertOpinionPath())
                        .industrialSafetyDeclarationPath(hfAppealDto.getIndustrialSafetyDeclarationPath())
                        .insurancePolicyPath(hfAppealDto.getInsurancePolicyPath())
                        .projectDocumentationPath(hfAppealDto.getProjectDocumentationPath())
                        .replyLetterPath(hfAppealDto.getReplyLetterPath())
                        .identificationCardPath(hfAppealDto.getIdentificationCardPath())
                        .receiptPath(hfAppealDto.getReceiptPath())
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
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO", "Id", id));

        Profile profile = profileRepository.findByTin(dto.legalTin()).orElseThrow(() -> new ResourceNotFoundException("Profile", "Tin", dto.legalTin()));

        hazardousFacility.setLegalTin(dto.legalTin());
        hazardousFacility.setLegalName(profile.getLegalName());
        hazardousFacility.setRegionId(dto.regionId());
        hazardousFacility.setDistrictId(dto.districtId());
        hazardousFacility.setLegalAddress(profile.getLegalAddress());
        hazardousFacility.setPhoneNumber(dto.phoneNumber());
        hazardousFacility.setEmail(dto.email());
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

        hazardousFacility.setAppointmentOrderPath(dto.appointmentOrderPath());
        hazardousFacility.setCadastralPassportPath(dto.cadastralPassportPath());
        hazardousFacility.setCertificationPath(dto.certificationPath());
        hazardousFacility.setPermitPath(dto.permitPath());
        hazardousFacility.setIndustrialSafetyDeclarationPath(dto.industrialSafetyDeclarationPath());
        hazardousFacility.setLicensePath(dto.licensePath());
        hazardousFacility.setEcologicalConclusionPath(dto.ecologicalConclusionPath());
        hazardousFacility.setExpertOpinionPath(dto.expertOpinionPath());
        hazardousFacility.setInsurancePolicyPath(dto.insurancePolicyPath());
        hazardousFacility.setProjectDocumentationPath(dto.projectDocumentationPath());
        hazardousFacility.setReplyLetterPath(dto.replyLetterPath());
        hazardousFacility.setIdentificationCardPath(dto.identificationCardPath());
        hazardousFacility.setDeviceTestingPath(dto.deviceTestingPath());
        hazardousFacility.setReceiptPath(dto.receiptPath());
        repository.save(hazardousFacility);
    }

    @Override
    public void deregister(UUID id, HfDeregisterDto dto) {
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", id));

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
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", id));

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
    public Page<HfCustom> getAll(User user, int page, int size, Long tin, String registryNumber, Integer regionId, LocalDate startDate, LocalDate endDate) {
        return repository.getHfCustoms(user, page, size, tin, registryNumber, regionId, startDate, endDate);
    }

    private HfAppealDto parseJsonData(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        try {
            return objectMapper.treeToValue(jsonNode, HfAppealDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON deserialization error", e);
        }
    }
}
