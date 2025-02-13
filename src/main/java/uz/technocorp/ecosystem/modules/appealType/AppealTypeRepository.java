package uz.technocorp.ecosystem.modules.appealType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealTypeRepository extends JpaRepository<AppealType, Integer> {

    List<AppealType> findAllByName(String name);

//    @Query(value = """
//            select *
//            from appeal_type
//            where name ilike concat('%', :name, '%')
//            """, nativeQuery = true)
    Page<AppealType> findAllPagesByName(Pageable pageable, String name);
}
