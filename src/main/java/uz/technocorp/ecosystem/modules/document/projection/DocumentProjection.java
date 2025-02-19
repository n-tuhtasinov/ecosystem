package uz.technocorp.ecosystem.modules.document.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface DocumentProjection {

    UUID getId();
    String getName();
    Boolean getAgreed();
    String getPath();
}
