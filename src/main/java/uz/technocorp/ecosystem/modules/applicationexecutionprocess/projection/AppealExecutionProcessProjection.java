package uz.technocorp.ecosystem.modules.applicationexecutionprocess.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface AppealExecutionProcessProjection {

    UUID getId();
    String getDescription();
    UUID getAppealId();
    String getExecutorName();
}
