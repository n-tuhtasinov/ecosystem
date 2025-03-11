package uz.technocorp.ecosystem.modules.hazardousfacilityappeal.helper;

import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.projection.HazardousFacilityAppealView;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.projection.AppealExecutionProcessProjection;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public record HazardousFacilityAppealDto(HazardousFacilityAppealView object, List<AppealExecutionProcessProjection> executionProcessList, List<DocumentProjection> documentProjections) {
}
