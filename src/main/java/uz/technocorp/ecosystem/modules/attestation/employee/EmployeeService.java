package uz.technocorp.ecosystem.modules.attestation.employee;

import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeDeleteDto;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.EmployeeListDto;
import uz.technocorp.ecosystem.modules.attestation.employee.dto.PositionDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
public interface EmployeeService {

    List<PositionDto> getPositions();

    Integer add(User user, EmployeeListDto dto);

    void updateEmployees(User user, EmployeeListDto dto);

    Integer deleteByPinList(User user, EmployeeDeleteDto dto);
}
