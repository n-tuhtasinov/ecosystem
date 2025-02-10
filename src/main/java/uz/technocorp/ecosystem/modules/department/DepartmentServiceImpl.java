package uz.technocorp.ecosystem.modules.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
}
