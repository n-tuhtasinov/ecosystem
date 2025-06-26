package uz.technocorp.ecosystem.modules.employee;

import uz.technocorp.ecosystem.modules.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeLevelDto;
import uz.technocorp.ecosystem.modules.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
public interface EmployeeService {

    List<EmployeeLevelDto> getEmployeeLevels();

    Integer add(User user, EmployeeListDto dto);

    void updateEmployees(User user, EmployeeListDto dto);

    Integer deleteByPinList(User user, EmployeeDeleteDto dto);

    List<Employee> getEmployeesByHf(UUID id, List<String> pinList);
}
