package uz.technocorp.ecosystem.modules.office;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.office.projection.OfficeView;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;

import java.util.Optional;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface OfficeRepository extends JpaRepository<Office, Integer> {

    @Query("select o.id as id, o.name as name, r.name as region from Office o join o.region r")
    Page<OfficeView> getAll(Pageable pageable);

    Optional<OfficeViewById> getOfficeById(Integer officeId);

    Optional<Office> getOfficeByRegionId(Integer regionId);
}
