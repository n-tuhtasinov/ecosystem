package uz.technocorp.ecosystem.modules.department;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.department.dto.DepartmentDto;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface DepartmentService {
    void create(DepartmentDto dto);

    void update(Integer departmentId, @Valid DepartmentDto dto);

    void delete(Integer departmentId);

    Page<Department> getAll(Map<String, String> params);

    List<Department> getAllBySelect();

    Department getById(Integer departmentId);
}
