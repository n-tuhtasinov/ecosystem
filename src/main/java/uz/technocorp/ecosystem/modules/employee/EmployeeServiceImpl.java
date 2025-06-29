package uz.technocorp.ecosystem.modules.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeAddDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeLevelDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
import uz.technocorp.ecosystem.modules.employee.view.EmployeeSelectView;
import uz.technocorp.ecosystem.modules.employee.view.EmployeeView;
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
        for (EmployeeAddDto dto : employeeDto.getEmployeeList()) {
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
        List<String> pinsToUpdate = listDto.getEmployeeList().stream().map(EmployeeAddDto::getPin).toList();

        // Get all employee by pinList
        Map<String, Employee> employeeMap = repository.findByHfIdAndPinIn(listDto.getHfId(), pinsToUpdate)
                .stream().collect(Collectors.toMap(Employee::getPin, Function.identity()));

        for (EmployeeAddDto dto : listDto.getEmployeeList()) {
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
    public List<EmployeeSelectView> getEmployeesByHf(UUID hfId) {
        return repository.findAllByHfId(hfId).stream().map(e -> new EmployeeSelectView(e.getId(), e.getPin(), e.getFullName())).toList();
    }

    @Override
    public EmployeeView getById(UUID employeeId) {
        return repository.findById(employeeId).map(this::map).orElseThrow(() -> new ResourceNotFoundException("Xodim", "ID", employeeId));
    }

    // MAPPER
    private EmployeeView map(Employee employee) {
        EmployeeView view = new EmployeeView();
        view.setId(employee.getId());
        view.setPin(employee.getPin());
        view.setName(employee.getFullName());
        view.setProfession(employee.getProfession());
        view.setCertNumber(employee.getCertNumber());
        view.setDateOfEmployment(employee.getDateOfEmployment());
        view.setCertDate(employee.getCertDate());
        view.setCertExpiryDate(employee.getCertExpiryDate());
        view.setCtcTrainingFromDate(employee.getCtcTrainingFromDate());
        view.setCtcTrainingToDate(employee.getCtcTrainingToDate());
        view.setLevel(employee.getLevel().getValue());
        view.setHfName(employee.getHfName());

        return view;
    }

    private HazardousFacility checkAndGetHf(UUID profileId, UUID hfId) {
        return hfService.findByIdAndProfileId(hfId, profileId);
    }

    private void setFields(Employee employee, EmployeeAddDto dto, HazardousFacility hf) {
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
