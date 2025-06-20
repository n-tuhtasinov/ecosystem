package uz.technocorp.ecosystem.modules.attestation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.attestation.employee.Employee;
import uz.technocorp.ecosystem.modules.attestation.employee.EmployeeService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements AttestationService {

    private final AppealService appealService;
    private final EmployeeService employeeService;
    private final HazardousFacilityService hfService;
    private final AttestationRepository repository;

    @Override
    @Transactional
    public void create(User user, SignedAppealDto<AttestationDto> signedDto, HttpServletRequest request) {
        /*AttestationDto dto = signedDto.getDto();

        // Check hf by profile
        HazardousFacility hf = checkAndGetHf(dto.getHfId(), user.getProfileId());

        // Get employees by pinList
        List<Employee> employeeList = employeeService.getEmployeesByHf(hf.getId(), dto.getPinList());

        // Check employee position
        checkEmployeesLevel(employeeList, dto.getDirection());

        setFields(dto, hf, employeeList);

        UUID appealId = appealService.saveAndSign(user, signedDto, request);

        List<Attestation> list = new ArrayList<>();
        for (Employee employee : employeeList) {
            Attestation attestation = getAttestation(employee, appealId, hf);
            list.add(attestation);
        }

        repository.saveAll(list);*/
    }

    private Attestation getAttestation(Employee employee, UUID appealId, HazardousFacility hf) {
        Attestation attestation = new Attestation();

        attestation.setEmployeePin(employee.getPin());
        attestation.setEmployeeName(employee.getFullName());
        attestation.setEmployeeLevel(employee.getLevel());
        attestation.setAppealId(appealId);
        attestation.setLegalTin(hf.getLegalTin());
        attestation.setLegalName(hf.getLegalName());
        attestation.setLegalAddress(hf.getLegalAddress());
        attestation.setHfId(hf.getId());
        attestation.setHfName(hf.getName());
        attestation.setHfAddress(hf.getAddress());
        attestation.setRegionId(hf.getRegionId());
        attestation.setDistrictId(hf.getDistrictId());

        return attestation;
    }

    private void setFields(AttestationDto dto, HazardousFacility hf, List<Employee> employeeList) {
        dto.setRegionId(hf.getRegionId());
        dto.setDistrictId(hf.getDistrictId());

    }

    private void checkEmployeesLevel(List<Employee> employees, Integer direction) {
        if (!employees.stream().allMatch(employee -> employee.getLevel().direction.equals(direction))) {
            throw new CustomException("Kiritilgan xodim tanlangan yo'nalishda emas");
        }
    }

    private HazardousFacility checkAndGetHf(UUID profileId, UUID hfId) {
        return hfService.findByIdAndProfileId(profileId, hfId);
    }
}
