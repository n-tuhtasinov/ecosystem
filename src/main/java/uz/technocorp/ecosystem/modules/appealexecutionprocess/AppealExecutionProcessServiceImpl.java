package uz.technocorp.ecosystem.modules.appealexecutionprocess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.dto.AppealExecutionProcessDto;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.projection.AppealExecutionProcessProjection;
import uz.technocorp.ecosystem.modules.department.DepartmentService;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppealExecutionProcessServiceImpl implements AppealExecutionProcessService {

    private final AppealExecutionProcessRepository repository;
    private final DepartmentService departmentService;
    private final ProfileService profileService;
    private final OfficeService officeService;
    private final UserService userService;

    @Override
    public void create(AppealExecutionProcessDto dto) {
        AppealExecutionProcess process = repository.save(
                new AppealExecutionProcess(dto.appealId(), dto.appealStatus(), dto.description()));

        // Write to log
        writeLog(process);
    }

    private void writeLog(AppealExecutionProcess process) {
        User user = userService.findById(process.getCreatedBy());
        Profile profile = profileService.getProfile(user.getProfileId());
        String departmentName = "-";
        String officeName = "-";
        if (user.getRole() != Role.LEGAL && user.getRole() != Role.INDIVIDUAL) {
            if (profile.getDepartmentId() != null) {
                departmentName = departmentService.getById(profile.getDepartmentId()).getName();
            }

            if (profile.getOfficeId() != null) {
                officeName = officeService.findById(profile.getOfficeId()).getName();
            }
        }

        log.info("Bajaruvchi FIO: {}, Bajaruvchi Pinfl: {}, Hududiy bo'limi: {}, Departamenti yoki boshqarmasi: {}, Vaqti: {}, Amaliyoti: {}"
                , user.getName(), profile.getPin(), officeName, departmentName, process.getCreatedAt(), process.getAppealStatus().getLabel());
    }

    @Override
    public List<AppealExecutionProcessProjection> getAll(UUID appealId) {
        return repository.getAllByAppealId(appealId);
    }
}
