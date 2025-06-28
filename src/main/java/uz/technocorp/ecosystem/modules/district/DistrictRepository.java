package uz.technocorp.ecosystem.modules.district;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.district.projection.DistrictView;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewById;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewBySelect;

import java.util.List;
import java.util.Optional;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface DistrictRepository extends JpaRepository<District, Integer> {

    Optional<District> findBySoato(Integer soato);

    @Query(nativeQuery = true,
            value = """
                    SELECT d.id as id, d.name as name, r.name as region, d.soato
                    FROM "district-1" d
                             JOIN public.region r ON r.id = d.region_id
                    WHERE (:regionId IS NULL OR d.region_id = :regionId)
                      AND (:search IS NULL OR d.name ILIKE '%' || :search || '%')
                    """)
    Page<DistrictView> getAllByRegionIdAndName(Pageable pageable, Integer regionId, String search);

    @Query("SELECT d FROM District d WHERE :regionId IS NULL OR d.regionId = :regionId")
    List<DistrictViewBySelect> getAllBySelect(Integer regionId);

    Optional<DistrictViewById> getDistrictById(Integer districtId);


}
