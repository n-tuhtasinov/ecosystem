package uz.technocorp.ecosystem.modules.equipment.view;

import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.07.2025
 * @since v1.0
 */
public interface EquipmentRiskView {
    UUID getId();
    String getFactoryNumber();
    String getName();
    Long getLegalTin();
    String getAddress();
    String getLegalName();
    String getInspectorName();
    UUID getAssignId();
    Integer getScore();
}
