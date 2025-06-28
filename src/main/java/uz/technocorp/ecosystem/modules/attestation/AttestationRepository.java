package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationReportDto;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Repository
public interface AttestationRepository extends JpaRepository<Attestation, UUID>, JpaSpecificationExecutor<Attestation>, CustomAttestationRepository {

    Optional<Attestation> findByIdAndLegalTin(UUID attestationId, Long tin);

    Optional<Attestation> findByIdAndRegionId(UUID id, Integer regionId);

    List<Attestation> findAllByAppealIdAndEmployeeLevel(UUID appealId, EmployeeLevel employeeLevel);

    List<Attestation> findAllByAppealIdAndRegionIdAndEmployeeLevelNot(UUID appealId, Integer regionId, EmployeeLevel employeeLevel);

    List<Attestation> findAllByAppealIdAndExecutorIdAndRegionIdAndStatusAndEmployeeLevelNot(UUID appealId, UUID inspectorId, Integer regionId, AttestationStatus status, EmployeeLevel employeeLevel);

    List<Attestation> findAllByAppealIdAndLegalTin(UUID appealId, Long tin);

    @Query("select distinct(appealId) from Attestation where status = 'PENDING' and employeeLevel = 'LEADER' ")
    List<UUID> getAllPendingForCommittee();

    @Query(
            value = """
                    WITH report_data AS (
                        SELECT
                            a.hf_id,
                            MAX(a.legal_name) AS legal_name,
                            MAX(a.legal_tin) AS legal_tin,
                            MAX(a.legal_address) AS legal_address,
                            MAX(a.hf_name) AS hf_name,
                            MAX(a.hf_address) AS hf_address,
                            COUNT(CASE WHEN a.employee_level = 'LEADER' AND a.status = 'PASSED' THEN 1 END) AS leaders_passed,
                            COUNT(CASE WHEN a.employee_level = 'TECHNICIAN' AND a.status = 'PASSED' THEN 1 END) AS technicians_passed,
                            COUNT(CASE WHEN a.employee_level = 'EMPLOYEE' AND a.status = 'PASSED' THEN 1 END) AS employees_passed,
                            COUNT(DISTINCT CASE WHEN a.status = 'FAILED' THEN a.employee_pin END) AS failed_employees
                        FROM attestation a
                    """ + DYNAMIC_WHERE_CLAUSE + """
                        GROUP BY a.hf_id
                    ),
                    employee_counts AS (
                        SELECT
                            e.hf_id,
                            COUNT(e.id) AS total_employees
                        FROM employee e
                        WHERE e.hf_id IN (SELECT rd.hf_id FROM report_data rd)
                        GROUP BY e.hf_id
                    )
                    SELECT
                        rd.legal_name AS legalName,
                        rd.legal_tin AS legalTin,
                        rd.legal_address AS legalAddress,
                        rd.hf_name AS hfName,
                        rd.hf_address AS hfAddress,
                        COALESCE(ec.total_employees, 0) AS totalEmployees,
                        COALESCE(rd.leaders_passed, 0) AS leadersPassed,
                        COALESCE(rd.technicians_passed, 0) AS techniciansPassed,
                        COALESCE(rd.employees_passed, 0) AS employeesPassed,
                        COALESCE(rd.failed_employees, 0) AS failedEmployees
                    FROM report_data rd
                    LEFT JOIN employee_counts ec ON rd.hf_id = ec.hf_id
                    ORDER BY rd.hf_name ASC
                    """,
            countQuery = """
            SELECT COUNT(DISTINCT a.hf_id)
            FROM attestation a
            """ + DYNAMIC_WHERE_CLAUSE,
            nativeQuery = true
    )
    Page<AttestationReportDto> getAttestationReport(
            @Param("legalName") String legalName,
            @Param("legalTin") Long legalTin,
            @Param("hfName") String hfName,
            @Param("regionId") Integer regionId,
            @Param("appealId") UUID appealId,
            @Param("executorId") UUID executorId,
            @Param("status") String status,
            @Param("direction") Integer direction,
            Pageable pageable
    );

    String DYNAMIC_WHERE_CLAUSE = """
        WHERE
            (:legalName IS NULL OR LOWER(a.legal_name) LIKE LOWER(CONCAT(:legalName, '%')))
            AND (:legalTin IS NULL OR a.legal_tin = :legalTin)
            AND (:hfName IS NULL OR LOWER(a.hf_name) LIKE LOWER(CONCAT(:hfName, '%')))
            AND (:regionId IS NULL OR a.region_id = :regionId)
            AND (:appealId IS NULL OR a.appeal_id = :appealId)
            AND (:executorId IS NULL OR a.executor_id = :executorId)
            AND (:status IS NULL OR a.status = :status)
            AND (:direction IS NULL OR
                 (:direction = 1 AND a.employee_level = 'LEADER') OR
                 (:direction = 2 AND a.employee_level IN ('TECHNICIAN', 'EMPLOYEE'))
            )
        """;
}
