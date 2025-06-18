package uz.technocorp.ecosystem.modules.attestation.employee;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeDto;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final HazardousFacilityService hfService;
    private final EmployeeRepository repository;

    @Override
    public Integer add(User user, EmployeeListDto employeeDto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkHf(user.getProfileId(), employeeDto.getHfId());

        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto dto : employeeDto.getEmployeeList()) {
            Employee employee = new Employee();

            employee.setPin(dto.getPin());
            employee.setFullName(dto.getFullName());
            employee.setCertNumber(dto.getCertNumber());
            employee.setCertDate(dto.getCertDate());
            employee.setCertExpiryDate(dto.getCertExpiryDate());
            employee.setPosition(dto.getPosition());
            employee.setHfId(hf.getId());

            employees.add(employee);
        }

        return repository.saveAll(employees).size();
    }

    @Override
    @Transactional
    public void updateEmployees(User user, EmployeeListDto listDto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkHf(user.getProfileId(), listDto.getHfId());

        // Collect pin to list
        List<String> pinsToUpdate = listDto.getEmployeeList().stream().map(EmployeeDto::getPin).toList();

        // Get all employee by pinList
        Map<String, Employee> employeeMap = repository.findByHfIdAndPinIn(listDto.getHfId(), pinsToUpdate)
                .stream().collect(Collectors.toMap(Employee::getPin, Function.identity()));

        for (EmployeeDto dto : listDto.getEmployeeList()) {
            Employee employee = employeeMap.get(dto.getPin());
            if (employee != null) {
                employee.setCertNumber(dto.getCertNumber());
                employee.setCertDate(dto.getCertDate());
                employee.setCertExpiryDate(dto.getCertExpiryDate());
                employee.setPosition(dto.getPosition());
            }
        }

        repository.saveAll(employeeMap.values());
    }

    @Override
    public Integer deleteByPinList(User user, EmployeeDeleteDto dto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkHf(user.getProfileId(), dto.getHfId());

        return repository.deleteByHfIdAndPinIn(hf.getId(), dto.getPinList());
    }

    private HazardousFacility checkHf(UUID profileId, UUID hfId) {
        return hfService.findByIdAndProfileId(profileId, hfId);
    }
}
