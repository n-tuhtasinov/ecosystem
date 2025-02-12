package uz.technocorp.ecosystem.modules.dangerousObjectType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DangerousObjectTypeRepository extends JpaRepository<DangerousObjectType, Integer> {

    List<DangerousObjectType> findAllByName(String name);

    Page<DangerousObjectType> findAllPageByName(Pageable pageable, String search);
}
