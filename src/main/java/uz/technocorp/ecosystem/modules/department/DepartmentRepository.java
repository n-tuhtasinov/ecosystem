package uz.technocorp.ecosystem.modules.department;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByClassifier(Integer classifier);
}
