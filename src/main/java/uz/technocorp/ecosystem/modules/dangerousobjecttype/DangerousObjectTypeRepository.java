package uz.technocorp.ecosystem.modules.dangerousobjecttype;

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
public interface DangerousObjectTypeRepository extends JpaRepository<DangerousObjectType, Integer> {

    List<DangerousObjectType> findAllByName(String name);

    Page<DangerousObjectType> findAllPageByName(Pageable pageable, String search);
}
