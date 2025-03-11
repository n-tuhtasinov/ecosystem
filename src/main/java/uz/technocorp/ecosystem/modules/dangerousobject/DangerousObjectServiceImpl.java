package uz.technocorp.ecosystem.modules.dangerousobject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appealdangerousobject.AppealDangerousObject;
import uz.technocorp.ecosystem.modules.appealdangerousobject.AppealDangerousObjectRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 07.03.2025
 * @since v1.0
 */

@Service
@RequiredArgsConstructor
public class DangerousObjectServiceImpl implements DangerousObjectService {

    private final DangerousObjectRepository repository;
    private final AppealDangerousObjectRepository appealRepository;

    @Override
    public void create(UUID id) {

        AppealDangerousObject appealDangerousObject = appealRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));

        repository.save(
                new DangerousObject(
                        appealDangerousObject.getLegal_tin(),
                        appealDangerousObject.getLegalName(),
                        appealDangerousObject.getRegionId(),
                        appealDangerousObject.getDistrictId(),
                        appealDangerousObject.getProfileId(),
                        appealDangerousObject.getLegalAddress(),
                        appealDangerousObject.getPhoneNumber(),
                        appealDangerousObject.getEmail(),
                        appealDangerousObject.getUpperOrganization(),
                        appealDangerousObject.getName(),
                        appealDangerousObject.getAddress(),
                        appealDangerousObject.getId(),
                        appealDangerousObject.getDangerousObjectTypeId(),
                        appealDangerousObject.getExtraArea(),
                        appealDangerousObject.getDescription(),
                        appealDangerousObject.getObjectNumber()
                )
        );
    }
}
