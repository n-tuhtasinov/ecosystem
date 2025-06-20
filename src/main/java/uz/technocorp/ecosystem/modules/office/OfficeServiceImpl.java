package uz.technocorp.ecosystem.modules.office;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.office.dto.OfficeDto;
import uz.technocorp.ecosystem.modules.office.projection.OfficeView;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;
import uz.technocorp.ecosystem.shared.AppConstants;

import java.util.List;
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

    @Override
    public void create(OfficeDto dto) {
        officeRepository.save(Office.builder().name(dto.name()).regionId(dto.regionId()).build());
    }

    @Override
    public void update(Integer officeId, OfficeDto dto) {
        Office office = findById(officeId);
        office.setName(dto.name());
        office.setRegionId(dto.regionId());
        officeRepository.save(office);
    }

    @Override
    public void deleteById(Integer officeId) {
        officeRepository.deleteById(officeId);
    }

    @Override
    public Page<OfficeView> getAll(Map<String, String> params) {

        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "name");

        return officeRepository.getAll(pageable);
    }

    @Override
    public List<Office> getAllBySelect() {
        return officeRepository.findAll();
    }

    @Override
    public OfficeViewById getById(Integer officeId) {
        return officeRepository.getOfficeById(officeId).orElseThrow(() -> new ResourceNotFoundException("Hududiy bo'lim", "officeId", officeId));
    }

    @Override
    public Office findById(Integer officeId) {
        return officeRepository.findById(officeId).orElseThrow(() -> new ResourceNotFoundException("Hududiy bo'lim", "officeId", officeId));
    }

    @Override
    public Office findByRegionId(Integer regionId) {
        return officeRepository.getOfficeByRegionId(regionId).orElseThrow(() -> new ResourceNotFoundException("Hududiy bo'lim", "regionId", regionId));
    }

}
