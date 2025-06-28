package uz.technocorp.ecosystem.modules.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByHfIdAndPinIn(UUID hfId, List<String> pins);

    List<Employee> findAllByHfId(UUID hfId);

    @Transactional
    int deleteAllByHfIdAndPinIn(UUID hfId, List<String> pins);
}
