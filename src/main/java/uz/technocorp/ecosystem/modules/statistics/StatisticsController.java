package uz.technocorp.ecosystem.modules.statistics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.response.StatByRegistryDto;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealStatusView;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealTypeView;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {


    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/appeal-status")
    public ResponseEntity<?> getAppealStatus(@Valid AppealStatusFilterDto filterDto) {
        List<StatByAppealStatusView> count = statisticsService.getAppealStatus(filterDto);
        return ResponseEntity.ok(new ApiResponse(count));
    }

    @GetMapping("/appeal-type")
    public ResponseEntity<?> getAppealType(@Valid AppealTypeFilterDto filterDto) {
        List<StatByAppealTypeView> list =  statisticsService.getAppealType(filterDto);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @GetMapping("/registry")
    public ResponseEntity<?> getRegistry(@RequestParam(required = false) LocalDate date) {
        List<StatByRegistryDto> list = statisticsService.getRegistry(date);
        return ResponseEntity.ok(new ApiResponse(list));
    }
}
