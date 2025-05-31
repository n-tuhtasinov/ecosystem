package uz.technocorp.ecosystem.modules.inspection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionPageView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
public interface InspectionRepository extends JpaRepository<Inspection, UUID>, InspectionRepo {


    @Query(value = """
            select *
            from inspection
            where interval_id = :intervalId
            and tin = :tin
            """, nativeQuery=true)
    Optional<Inspection> findAllByTinAndIntervalId(Long tin, Integer intervalId);

}
