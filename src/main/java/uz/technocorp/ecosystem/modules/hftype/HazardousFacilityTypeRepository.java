package uz.technocorp.ecosystem.modules.hftype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityTypeRepository extends JpaRepository<HazardousFacilityType, Integer> {

    @Query(value = """
            select *
            from hazardous_facility_type
            where name ilike concat('%', :search, '%')
            """, nativeQuery = true)
    List<HazardousFacilityType> findAllByName(String search);

    @Query(value = """
            select *
            from hazardous_facility_type
            where name ilike concat('%', :search, '%')
            """, nativeQuery = true)
    Page<HazardousFacilityType> findAllPageByName(Pageable pageable, String search);
}
