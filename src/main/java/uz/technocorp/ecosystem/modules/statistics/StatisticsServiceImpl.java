package uz.technocorp.ecosystem.modules.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentCountByStatusView;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hf.view.HfCountByStatusView;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSourceService;
import uz.technocorp.ecosystem.modules.irs.view.IrsCountByStatusView;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.response.StatByRegistryDto;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealStatusView;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealTypeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {


    private final AppealRepository appealRepository;
    private final HazardousFacilityService hazardousFacilityService;
    private final OfficeService officeService;
    private final EquipmentService equipmentService;
    private final IonizingRadiationSourceService ionizingRadiationSourceService;

    @Override
    public List<StatByAppealStatusView> getAppealStatus(AppealStatusFilterDto filterDto) {
        if (filterDto.getStartDate() == null) {
            filterDto.setStartDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        }
        return appealRepository.countByAppealStatus(filterDto.getOwnerType().name(), filterDto.getStartDate(), filterDto.getEndDate());
    }

    @Override
    public List<StatByAppealTypeView> getAppealType(AppealTypeFilterDto dto) {
        Stream<AppealType> appealTypeStream = dto.getAppealTypes().isEmpty() ? Arrays.stream(AppealType.values()) : dto.getAppealTypes().stream();
        String[] appealTypes = appealTypeStream.map(Enum::name).toArray(String[]::new);

        if (dto.getStartDate() == null) {
            dto.setStartDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        }

        return appealRepository.countByAppealType(dto.getOwnerType().name(), dto.getStartDate(), dto.getEndDate(), appealTypes);
    }

    @Override
    public String getLabelByAppealType(String appealType) {
        return AppealType.valueOf(appealType).label;
    }

    @Override
    public List<StatByRegistryDto> getRegistry(LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }

        List<Office> offices = officeService.getAllBySelect();
        Office committee = Office.builder().regionId(null).name("Qo'mita").build();  //for adding qo'mita to the list
        offices.add(committee);
        List<StatByRegistryDto> statList = new ArrayList<>();

        for (Office office : offices) {
            StatByRegistryDto stat = new StatByRegistryDto();
            stat.setOfficeName(office.getName());

            if (office.getRegionId()==null){
                IrsCountByStatusView irsView = ionizingRadiationSourceService.countIrsStatusByDate(date);
                stat.setInactiveIrs(irsView.getInactive());
                stat.setActiveIrs(irsView.getActive());
                statList.add(stat);
                continue;
            }

            HfCountByStatusView hfView = hazardousFacilityService.countHfStatusByDateAndRegionId(date, office.getRegionId());
            EquipmentCountByStatusView equipmentView = equipmentService.countEquipmentStatusByDateAndRegionId(date, office.getRegionId());

            stat.setActiveHf(hfView.getActive());
            stat.setInactiveHf(hfView.getInactive());
            stat.setActiveEquipment(equipmentView.getActive());
            stat.setInactiveEquipment(equipmentView.getInactive());
            stat.setExpiredEquipment(equipmentView.getExpired());

            statList.add(stat);
        }
        return statList;
    }
}
