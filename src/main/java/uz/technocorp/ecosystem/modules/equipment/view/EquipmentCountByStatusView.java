package uz.technocorp.ecosystem.modules.equipment.view;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 21.08.2025
 * @since v1.0
 */
public interface EquipmentCountByStatusView {
    Integer getActive();
    Integer getInactive();
    Integer getExpired();
}
