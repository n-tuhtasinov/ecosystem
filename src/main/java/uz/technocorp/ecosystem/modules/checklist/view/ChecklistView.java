package uz.technocorp.ecosystem.modules.checklist.view;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
public interface ChecklistView {

    UUID getId();
    String getPath();
    Integer getTemplateId();
    String getTemplateName();
}
