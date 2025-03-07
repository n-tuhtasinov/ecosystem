package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.appeal.projection.AppealProjection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealRepository extends JpaRepository<Appeal, UUID> {

    @Query(value = """
            select cast(id as varchar) as id,
            cast(main_id as varchar) as mainId,
            legal_name as legalName,
            legal_tin as legalTin,
            status,
            inspector_name as inspectorName,
            region_name as regionName,
            district_name as districtName,
            address,
            email,
            phone_number as phoneNumber,
            appeal_type as appealType,
            deadline,
            to_char(created_at, 'YYYY-MM-DD') as date
            from appeal
            """, nativeQuery = true)
    Page<AppealProjection> getAppeals(Pageable pageable);
}
