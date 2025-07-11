package uz.technocorp.ecosystem.modules.accreditation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
public interface AccreditationRepository extends JpaRepository<Accreditation, UUID>, JpaSpecificationExecutor<Accreditation> {

    Optional<Accreditation> findByCertificateNumber(String certificateNumber);

    Optional<Accreditation> findByTinAndType(Long tin, AccreditationType type);

    @Query(nativeQuery = true, value = "select a.order_number from accreditation a where a.type = :accreditationType order by a.order_number desc limit 1")
    Optional<Long> getMaxNumber(AccreditationType accreditationType);
}
