package uz.technocorp.ecosystem.modules.office;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.office.projection.OfficeView;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface OfficeRepository extends JpaRepository<Office, Integer> {

    @Query("select o from Office o")
    Page<OfficeView> getAll(Pageable pageable);
}
