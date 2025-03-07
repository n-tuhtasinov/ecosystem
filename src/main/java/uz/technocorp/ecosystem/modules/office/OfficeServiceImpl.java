package uz.technocorp.ecosystem.modules.office;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.department.dto.DepartmentDto;
import uz.technocorp.ecosystem.modules.office.dto.OfficeDto;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public void create(OfficeDto dto) {
        Office office = officeRepository.save(Office.builder().name(dto.name()).build());

        dto.regionIds().forEach(regionId -> {
            Region region = regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "regionId", regionId));
            region.setOfficeId(office.getId());
            regionRepository.save(region);
        });
    }

    @Override
    public void update(Integer officeId, OfficeDto dto) {
        Office office = officeRepository.findById(officeId).orElseThrow(() -> new ResourceNotFoundException("Hududiy bo'lim", "officeId", officeId));
        office.setName(dto.name());
        office.setRegions(null); //remove all regions
        officeRepository.save(office);

        //update for new regions
        dto.regionIds().forEach(regionId -> {
            Region region = regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "regionId", regionId));
            region.setOfficeId(office.getId());
            regionRepository.save(region);
        });
    }

    @Override
    public void deleteById(Integer officeId) {
        //remove regions
        Office office = officeRepository.findById(officeId).orElseThrow(() -> new ResourceNotFoundException("Hududiy bo'lim", "officeId", officeId));
        office.setRegions(null);
        officeRepository.save(office);

        //delete office by id
        officeRepository.deleteById(officeId);
    }

}
