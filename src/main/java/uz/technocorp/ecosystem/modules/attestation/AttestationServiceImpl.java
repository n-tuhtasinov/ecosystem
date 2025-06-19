package uz.technocorp.ecosystem.modules.attestation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.attestation.employee.Employee;
import uz.technocorp.ecosystem.modules.attestation.employee.EmployeeService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements AttestationService {

    private final EmployeeService employeeService;
    private final HazardousFacilityService hfService;
    private final AttestationRepository repository;

    @Override
    @Transactional
    public void create(User user, AttestationDto dto) {
        HazardousFacility hf = hfService.findByIdAndProfileId(dto.getHfId(), user.getProfileId());

        List<Employee> employeeList = employeeService.getEmployeesByHf(hf.getId(), dto.getPinList());

        // Check employee position
        checkEmployeesPosition(employeeList, dto.getDirection());

        List<Attestation> list = new ArrayList<>();

        for (Employee employee : employeeList) {
            Attestation attestation = new Attestation();

//            attestation.set
        }

    }

    private void checkEmployeesPosition(List<Employee> employees, Integer direction) {
        if (!employees.stream().allMatch(employee -> employee.getPosition().direction.equals(direction))) {
            throw new CustomException("Kiritilgan xodim tanlangan yo'nalishda emas");
        }
    }

}
