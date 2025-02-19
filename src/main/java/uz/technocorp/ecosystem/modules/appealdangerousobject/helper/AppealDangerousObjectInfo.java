package uz.technocorp.ecosystem.modules.appealdangerousobject.helper;

import uz.technocorp.ecosystem.modules.appealdangerousobject.projection.AppealDangerousObjectProjection;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.projection.AppealExecutionProcessProjection;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public record AppealDangerousObjectInfo(AppealDangerousObjectProjection object, List<AppealExecutionProcessProjection> executionProcessList, List<DocumentProjection> documentProjections) {
}
