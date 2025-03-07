package uz.technocorp.ecosystem.modules.region;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.region.projection.RegionView;
import uz.technocorp.ecosystem.modules.region.projection.RegionViewBySelect;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query("SELECT r FROM Region r")
    Page<RegionView> getAll(Pageable pageable);

    @Query("SELECT r.id, r.name FROM Region r")
    List<RegionViewBySelect> getAllBySelect();
}
