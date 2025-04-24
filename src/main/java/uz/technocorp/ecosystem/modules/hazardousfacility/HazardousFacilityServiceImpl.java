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
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.hazardousfacility.dto.HfDto;
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
    public void create(UUID appealId) {

        Appeal appeal = appealRepository
                .findById(appealId)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", appealId));
        Integer maxSerialNumber = repository.findMaxSerialNumber();
        District district = districtRepository
                .findById(appeal.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", appeal.getDistrictId()));
        Region region = regionRepository
                .findById(appeal.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", appeal.getRegionId()));
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
                        .location(hfAppealDto.getLocation())
                        .hazardousSubstance(hfAppealDto.getHazardousSubstance())
                        .appealId(appeal.getId())
                        .hazardousFacilityTypeId(hfAppealDto.getHazardousFacilityTypeId())
                        .extraArea(hfAppealDto.getExtraArea())
                        .registryNumber(registryNumber)
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

    @Override
    public void create(HfDto dto) {
        repository.save(
                HazardousFacility.builder()
                        .legalTin(dto.legalTin())
                        .legalName(dto.legalName())
                        .regionId(dto.regionId())
                        .districtId(dto.districtId())
//                        .profileId(dto.profileId())
                        .legalAddress(dto.legalAddress())
                        .phoneNumber(dto.phoneNumber())
                        .email(dto.email())
                        .upperOrganization(dto.upperOrganization())
                        .name(dto.name())
                        .address(dto.address())
                        .location(dto.location())
                        .hazardousSubstance(dto.hazardousSubstance())
                        .spheres(dto.spheres())

                        .hazardousFacilityTypeId(dto.hazardousFacilityTypeId())
                        .extraArea(dto.extraArea())
                        .description(dto.description())
                        .registryNumber(dto.registryNumber())
                        .active(true)
                        .appointmentOrderPath(dto.appointmentOrderPath())
                        .cadastralPassportPath(dto.cadastralPassportPath())
                        .certificationPath(dto.certificationPath())
                        .permitPath(dto.permitPath())
                        .deviceTestingPath(dto.deviceTestingPath())
                        .licensePath(dto.licensePath())
                        .ecologicalConclusionPath(dto.ecologicalConclusionPath())
                        .expertOpinionPath(dto.expertOpinionPath())
                        .industrialSafetyDeclarationPath(dto.industrialSafetyDeclarationPath())
                        .insurancePolicyPath(dto.insurancePolicyPath())
                        .projectDocumentationPath(dto.projectDocumentationPath())
                        .replyLetterPath(dto.replyLetterPath())
                        .identificationCardPath(dto.identificationCardPath())
                        .receiptPath(dto.receiptPath())
                        .build());
    }

    @Override
    public void update(UUID id, HfDto dto) {
        HazardousFacility hazardousFacility = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO", "Id", id));
        hazardousFacility.setLegalTin(dto.legalTin());
        hazardousFacility.setLegalName(dto.legalName());
        hazardousFacility.setRegionId(dto.regionId());
        hazardousFacility.setDistrictId(dto.districtId());
        hazardousFacility.setLegalAddress(dto.legalAddress());
        hazardousFacility.setPhoneNumber(dto.phoneNumber());
        hazardousFacility.setEmail(dto.email());
        hazardousFacility.setUpperOrganization(dto.upperOrganization());
        hazardousFacility.setName(dto.name());
        hazardousFacility.setAddress(dto.address());
        hazardousFacility.setLocation(dto.location());
        hazardousFacility.setHazardousSubstance(dto.hazardousSubstance());
        hazardousFacility.setHazardousFacilityTypeId(dto.hazardousFacilityTypeId());
        hazardousFacility.setExtraArea(dto.extraArea());
        hazardousFacility.setDescription(dto.description());
        hazardousFacility.setRegistryNumber(dto.registryNumber());
        hazardousFacility.setSpheres(dto.spheres());

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
    public void deregister(UUID id, Map<String, String> dto) {
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
