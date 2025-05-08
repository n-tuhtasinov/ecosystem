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
public interface HfTypeRepository extends JpaRepository<HfType, Integer> {

    @Query(value = """
            select *
            from hf_type
            where name ilike concat('%', :search, '%')
            """, nativeQuery = true)
    List<HfType> findAllByName(String search);

    @Query(value = """
            select *
            from hf_type
            where name ilike concat('%', :search, '%')
            """, nativeQuery = true)
    Page<HfType> findAllPageByName(Pageable pageable, String search);
}
