package uz.technocorp.ecosystem.modules.department;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.department.dto.DepartmentDto;

import java.util.List;
import java.util.Map;

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

    @Override
    public void create(DepartmentDto dto) {
        departmentRepository.save(Department.builder().name(dto.name()).classifier(dto.classifier()).build());
    }

    @Override
    public void update(Integer departmentId, DepartmentDto dto) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department yoki bo'lim", "ID", departmentId));
        department.setName(dto.name());
        department.setClassifier(dto.classifier());
        departmentRepository.save(department);
    }

    @Override
    public void delete(Integer departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Page<Department> getAll(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "name");

        return departmentRepository.findAll(pageable);
    }

    @Override
    public List<Department> getAllBySelect() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(Integer departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("Bo'lim yoki departament", "ID", departmentId));
    }

    @Override
    public Department findByClassifier(Integer classifier) {
        return departmentRepository.findByClassifier(classifier).orElseThrow(()->new ResourceNotFoundException("Bo'lim yoki departament", "klassifikator raqami", classifier));
    }
}
