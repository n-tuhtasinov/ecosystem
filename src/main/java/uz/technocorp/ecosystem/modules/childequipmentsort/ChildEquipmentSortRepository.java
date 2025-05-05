package uz.technocorp.ecosystem.modules.childequipmentsort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortView;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewById;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewBySelect;

import java.util.List;
import java.util.Optional;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface ChildEquipmentSortRepository extends JpaRepository<ChildEquipmentSort, Integer> {

    @Query(nativeQuery = true, value = """
            select s.id         as id,
                   s.created_at as createdAt,
                   s.name       as name,
                   ce.name      as childEquipment
            from child_equipment_sort s
                     join public.child_equipment ce on ce.id = s.child_equipment_id
            where (:childEquipmentId is null or s.child_equipment_id = :childEquipmentId)
            """)
    Page<ChildEquipmentSortView> getAllByPage(Pageable pageable, Integer childEquipmentId);

    List<ChildEquipmentSortViewBySelect> findByChildEquipmentId(Integer childEquipmentId);

    @Query("select s from ChildEquipmentSort s where s.id = :id")
    Optional<ChildEquipmentSortViewById> getSortById(Integer id);

}
