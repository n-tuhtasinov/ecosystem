package uz.technocorp.ecosystem.modules.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeLevelDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.*;
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
    public List<EmployeeLevelDto> getEmployeeLevels() {
        return Arrays.stream(EmployeeLevel.values()).map(e -> new EmployeeLevelDto(e.getDirection(), e.getValue())).toList();
    }

    @Override
    public Integer add(User user, EmployeeListDto employeeDto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkAndGetHf(user.getProfileId(), employeeDto.getHfId());

        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto dto : employeeDto.getEmployeeList()) {
            Employee employee = new Employee();

            employee.setPin(dto.getPin());
            employee.setFullName(dto.getFullName());

            // Set other fields
            setFields(employee, dto, hf);

            employees.add(employee);
        }

        return repository.saveAll(employees).size();
    }

    @Override
    @Transactional
    public void updateEmployees(User user, EmployeeListDto listDto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkAndGetHf(user.getProfileId(), listDto.getHfId());

        // Collect pin to list
        List<String> pinsToUpdate = listDto.getEmployeeList().stream().map(EmployeeDto::getPin).toList();

        // Get all employee by pinList
        Map<String, Employee> employeeMap = repository.findByHfIdAndPinIn(listDto.getHfId(), pinsToUpdate)
                .stream().collect(Collectors.toMap(Employee::getPin, Function.identity()));

        for (EmployeeDto dto : listDto.getEmployeeList()) {
            Employee employee = employeeMap.get(dto.getPin());
            if (employee != null) {
                // Set changed fields
                setFields(employee, dto, hf);
            }
        }

        repository.saveAll(employeeMap.values());
    }

    @Override
    public Integer deleteByPinList(User user, EmployeeDeleteDto dto) {
        // Check if hf belongs to the profile
        HazardousFacility hf = checkAndGetHf(user.getProfileId(), dto.getHfId());

        return repository.deleteAllByHfIdAndPinIn(hf.getId(), dto.getPinList());
    }

    @Override
    public List<Employee> getEmployeesByHf(UUID id, List<String> pinList) {
        return repository.findByHfIdAndPinIn(id, pinList);
    }

    private HazardousFacility checkAndGetHf(UUID profileId, UUID hfId) {
        return hfService.findByIdAndProfileId(hfId, profileId);
    }

    private void setFields(Employee employee, EmployeeDto dto, HazardousFacility hf) {
        employee.setProfession(dto.getProfession());
        employee.setCertNumber(dto.getCertNumber());
        employee.setDateOfEmployment(dto.getDateOfEmployment());
        employee.setCertDate(dto.getCertDate());
        employee.setCertExpiryDate(dto.getCertExpiryDate());
        employee.setCtcTrainingFromDate(dto.getCtcTrainingFromDate());
        employee.setCtcTrainingToDate(dto.getCtcTrainingToDate());
        employee.setLevel(dto.getLevel());
        employee.setHfId(hf.getId());
        employee.setHfName(hf.getName());
    }
}
