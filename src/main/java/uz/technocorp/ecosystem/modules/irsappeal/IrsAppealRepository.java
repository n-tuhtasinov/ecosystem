package uz.technocorp.ecosystem.modules.irsappeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
public interface IrsAppealRepository extends JpaRepository<IrsAppeal, UUID> {

    @Query("select a from IrsAppeal a order by a.orderNumber desc limit 1")
    Integer getMaxSequence();
}
