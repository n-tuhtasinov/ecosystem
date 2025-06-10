package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDeregisterDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsParams;
import uz.technocorp.ecosystem.modules.irs.view.IrsView;
import uz.technocorp.ecosystem.modules.irs.view.IrsViewById;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.04.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceService {

    void create(Appeal appeal);
    void update(UUID id, IrsDto dto);
    void deregister(UUID id, IrsDeregisterDto dto);
    Page<IrsView> getAll(User user, IrsParams irsParams);
    Page<HfPageView> getAllForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    IrsViewById getById(UUID irsId);
}
