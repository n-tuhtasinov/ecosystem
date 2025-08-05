package uz.technocorp.ecosystem.modules.attestation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationConductDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationParamsDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationReportDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationReportFilterDto;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationDirection;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.attestation.view.AttestationView;
import uz.technocorp.ecosystem.modules.attestationappeal.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.attestationappeal.dto.EmployeeDto;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements AttestationService {

    private final AttestationSpecification specification;
    private final AttachmentService attachmentService;
    private final AttestationRepository repository;
    private final ProfileService profileService;

    @Override
    public Page<?> getAllByParams(User user, AttestationParamsDto dto) {
        Pageable pageable = getPageable(dto.getPage(), dto.getSize());

        return switch (user.getRole()) {
            case MANAGER, HEAD -> getAllFromCommittee(dto, pageable);
            case REGIONAL, INSPECTOR -> getAllForRegionalAndInspector(user, dto, pageable);
            case LEGAL -> getAllForLegal(user, dto, pageable);
            default -> throw new CustomException("Sizda attestatsiya ko'rish ruhsati yo'q");
        };
    }

    @Override
    public AttestationView getById(User user, UUID attestationId) {
        return switch (user.getRole()) {
            case MANAGER, HEAD, CHAIRMAN -> getByIdForCommittee(attestationId);
            case INSPECTOR, REGIONAL -> getByIdAndProfileRegion(user, attestationId);
            case LEGAL -> getByIdForLegal(user, attestationId);
            default -> throw new ResourceNotFoundException("Attestatsiya" + "ID" + attestationId);
        };
    }

    @Override
    public List<AttestationView> getByAppeal(User user, UUID appealId) {
        return switch (user.getRole()) {
            case MANAGER, HEAD, CHAIRMAN ->
                    repository.findAllByAppealIdAndEmployeeLevel(appealId, EmployeeLevel.LEADER).stream().map(this::map).toList();
            case INSPECTOR, REGIONAL -> getAllByAppealAndRegion(user, appealId);
            case LEGAL -> getAllByTinAndAppealId(user, appealId);
            default -> throw new ResourceNotFoundException("Ariza" + "ID" + appealId);
        };
    }

    @Override
    public void create(Appeal appeal) {
        AttestationDto dto = JsonParser.parseJsonData(appeal.getData(), AttestationDto.class);

        List<Attestation> list = new ArrayList<>();
        for (EmployeeDto employee : dto.getEmployeeList()) {
            Attestation attestation = Attestation.builder()
                    .employeePin(employee.getPin())
                    .employeeName(employee.getFullName())
                    .employeeLevel(employee.getLevel())
                    .dateOfAttestation(appeal.getDeadline())
                    .expiryDate(null)
                    .appealId(appeal.getId())
                    .legalTin(appeal.getOwnerIdentity())
                    .legalName(appeal.getOwnerName())
                    .legalAddress(appeal.getOwnerAddress())
                    .hfId(dto.getHfId())
                    .hfName(dto.getHfName())
                    .hfAddress(appeal.getAddress())
                    .status(AttestationStatus.PENDING)
                    .regionId(appeal.getRegionId())
                    .districtId(appeal.getDistrictId())
                    .executorId(appeal.getExecutorId())
                    .build();

            list.add(attestation);
        }

        repository.saveAll(list);
    }

    @Override
    public void conduct(User user, AttestationConductDto dto) {
        List<Attestation> list = switch (user.getRole()) {
            case MANAGER, HEAD -> repository.findAllByAppealIdAndEmployeeLevel(dto.getAppealId(), EmployeeLevel.LEADER);
            case INSPECTOR -> getPendingAttForInspector(user, dto);
            default -> throw new CustomException("Sizda xodimlarni attestatsiyadan o'tkazish huquqi yo`q!");
        };

        if (list == null || list.isEmpty()) {
            throw new CustomException("Sizga biriktirilgan attestatsiya xodimlari topilmadi");
        }

        for (Attestation att : list) {
            att.setDateOfAttestation(dto.getDateOfAttestation());
            att.setExpiryDate(dto.getDateOfAttestation().plusYears(att.getEmployeeLevel().getTerm()));
            att.setStatus(dto.getResult().get(att.getEmployeePin()));
            att.setReportPdfPath(dto.getFilePath());
        }

        repository.saveAll(list);

        // Delete file from attachment
        attachmentService.deleteByPath(dto.getFilePath());
    }

    private Page<AttestationReportDto> getAllFromCommittee(AttestationParamsDto dto, Pageable pageable) {
        AttestationReportFilterDto filter = new AttestationReportFilterDto();
        filter.setLegalTin(isTin(dto.getSearch()) ? Long.parseLong(dto.getSearch()) : null);
        filter.setLegalName(isTin(dto.getSearch()) ? null : dto.getSearch());
        filter.setHfName(dto.getHfName());
        filter.setRegionId(dto.getRegionId());
        filter.setDirection(AttestationDirection.COMMITTEE.getValue());

        return getAttestationReport(filter, pageable);
    }

    private Page<AttestationReportDto> getAllForRegionalAndInspector(User user, AttestationParamsDto dto, Pageable pageable) {
        Integer regionId = getRegionId(user);

        AttestationReportFilterDto filter = new AttestationReportFilterDto();
        filter.setRegionId(regionId);
        filter.setLegalTin(isTin(dto.getSearch()) ? Long.parseLong(dto.getSearch()) : null);
        filter.setLegalName(isTin(dto.getSearch()) ? null : dto.getSearch());
        filter.setHfName(dto.getHfName());
        filter.setDirection(AttestationDirection.REGIONAL.getValue());

        // For inspector
        if (user.getRole().equals(Role.INSPECTOR)) {
            filter.setExecutorId(user.getId());
        }

        return getAttestationReport(filter, pageable);
    }

    private Page<AttestationView> getAllForLegal(User user, AttestationParamsDto params, Pageable pageable) {
        // LegalTin
        Specification<Attestation> legalTin = specification.hasLegalTin(profileService.getProfileIdentity(user.getProfileId()));

        // Search
        Specification<Attestation> hasSearch = specification.hasSearch(params.getSearch());

        // Get Preventions from DB
        Page<Attestation> attestations = repository.findAll(where(legalTin).and(hasSearch), pageable);

        // Create PageImpl
        return new PageImpl<>(attestations.stream().map(this::map).toList(), pageable, attestations.getTotalElements());
    }

    private List<Attestation> getPendingAttForInspector(User user, AttestationConductDto dto) {
        Integer regionId = getRegionId(user);
        return repository.findAllByAppealIdAndExecutorIdAndRegionIdAndStatusAndEmployeeLevelNot(
                dto.getAppealId(), user.getId(), regionId, AttestationStatus.PENDING, EmployeeLevel.LEADER/*Excluding the LEADER*/);
    }

    private List<AttestationView> getAllByTinAndAppealId(User user, UUID appealId) {
        Long tin = profileService.getProfileIdentity(user.getProfileId());
        return repository.findAllByAppealIdAndLegalTin(appealId, tin).stream().map(this::map).toList();
    }

    private List<AttestationView> getAllByAppealAndRegion(User user, UUID appealId) {
        Integer regionId = getRegionId(user);
        return repository.findAllByAppealIdAndRegionIdAndEmployeeLevelNot(appealId, regionId, EmployeeLevel.LEADER/*Excluding the LEADER*/)
                .stream().map(this::map).toList();
    }

    private AttestationView getByIdForCommittee(UUID attestationId) {
        return repository.findById(attestationId).map(this::map).orElseThrow(
                () -> new ResourceNotFoundException("Attestatsiya" + "ID" + attestationId)
        );
    }

    private AttestationView getByIdAndProfileRegion(User user, UUID attestationId) {
        Integer regionId = getRegionId(user);

        return repository.findByIdAndRegionId(attestationId, regionId).map(this::map).orElseThrow(
                () -> new ResourceNotFoundException("Attestatsiya" + "ID" + attestationId));
    }

    private AttestationView getByIdForLegal(User user, UUID attestationId) {
        Long tin = profileService.getProfileIdentity(user.getProfileId());

        return repository.findByIdAndLegalTin(attestationId, tin).map(this::map).orElseThrow(
                () -> new ResourceNotFoundException("Attestatsiya", "ID", attestationId));
    }

    private Integer getRegionId(User user) {
        return profileService.getProfile(user.getProfileId()).getRegionId();
    }

    private Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "hfName"));
    }

    private Page<AttestationReportDto> getAttestationReport(AttestationReportFilterDto filter, Pageable pageable) {
        String statusStr = filter.getStatus() != null ? filter.getStatus().name() : null;

        return repository.getAttestationReport(
                filter.getLegalName(),
                filter.getLegalTin(),
                filter.getHfName(),
                filter.getRegionId(),
                filter.getAppealId(),
                filter.getExecutorId(),
                statusStr,
                filter.getDirection(),
                pageable
        );
    }

    private Boolean isTin(String search) {
        try {
            if (search.length() == 9) {
                Long.parseLong(search);
                return true;
            }
        } catch (RuntimeException ignored) {
        }
        return false;
    }

    // MAPPER
    private AttestationView map(Attestation a) {
        return AttestationView.builder()
                .employeePin(a.getEmployeePin())
                .employeeName(a.getEmployeeName())
                .employeeLevel(a.getEmployeeLevel())
                .expiryDate(a.getExpiryDate())
                .appealId(a.getAppealId())
                .legalTin(a.getLegalTin())
                .legalName(a.getLegalName())
                .legalAddress(a.getLegalAddress())
                .hfName(a.getHfName())
                .hfAddress(a.getHfAddress())
                .status(a.getStatus())
                .build();
    }

}
