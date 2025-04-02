//package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.UUID;
//
///**
// * @author Rasulov Komil
// * @version 1.0
// * @created 17.03.2025
// * @since v1.0
// */
//public interface HazardousFacilityModificationAppealRepository extends JpaRepository<HazardousFacilityModificationAppeal, UUID> {
//
//    @Query("SELECT h.orderNumber FROM HazardousFacilityModificationAppeal h ORDER BY h.orderNumber DESC LIMIT 1")
//    Long findMaxOrderNumber();
//}
