package uz.technocorp.ecosystem.modules.template.projection;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
public interface TemplateView {
    Integer getId();

    String getName();

    String getDescription();

    String getType();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getContent();
}
