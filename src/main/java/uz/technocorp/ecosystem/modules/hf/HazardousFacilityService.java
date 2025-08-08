package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hf.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfDto;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.dto.HfPeriodicUpdateDto;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;
import uz.technocorp.ecosystem.modules.hf.view.HfViewById;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityService {

    void create(Appeal appeal);

    //    void create(HfDto dto);
    void update(UUID id, HfDto dto);

    void deregister(UUID id, HfDeregisterDto dto);

    void periodicUpdate(UUID id, HfPeriodicUpdateDto dto);

    List<HfSelectView> findAllByUser(User user, String registryNumber);

    Page<HfCustom> getAll(User user, HfParams params);

    String getHfNameById(UUID hfId);

    Page<HfPageView> getAllForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    HfViewById getById(UUID hfId);

    HazardousFacility findByIdAndProfileId(UUID id, UUID profileId);

    HazardousFacility findByRegistryNumber(String hfRegistryNumber);

    Long getCount(User user);

    String createHfRegistryPdf(Appeal appeal, String registryNumber, HfAppealDto hfAppealDto, String now);

    List<HazardousFacility> getAllByTin(Long tin);
}
