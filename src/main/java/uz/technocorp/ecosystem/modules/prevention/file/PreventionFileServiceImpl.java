package uz.technocorp.ecosystem.modules.prevention.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Suxrob
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class PreventionFileServiceImpl implements PreventionFileService {

    private final ProfileService profileService;
    private final PreventionFileRepository repository;

    @Override
    public PreventionFile get(User user, Integer year) {
        Profile profile = profileService.getProfile(user.getProfileId());

        return repository.findByYearAndRegionId(year, profile.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "yil", year));
    }

    @Override
    public PreventionFile findByYearAndRegion(Integer year, Integer regionId) {
        return repository.findByYearAndRegionId(year, regionId).orElse(null);
    }

    @Override
    public void create(User user, String filePath) {
        Integer currentYear = LocalDate.now().getYear();
        Profile profile = profileService.getProfile(user.getProfileId());

        PreventionFile file = repository.findByYearAndRegionId(currentYear, profile.getRegionId()).orElse(new PreventionFile());

        file.setPath(filePath);
        file.setYear(currentYear);
        file.setRegionId(profile.getRegionId());

        repository.save(file);
    }

    @Override
    public void delete(User user, String filePath) {
        Integer currentYear = LocalDate.now().getYear();
        Profile profile = profileService.getProfile(user.getProfileId());

        PreventionFile file = repository.findByPath(filePath).orElseThrow(() -> new ResourceNotFoundException("File", "yil", filePath));

        if (!Objects.equals(file.getYear(), currentYear)) {
            throw new CustomException("Siz faqat hozirgi yil faylini o'chira olasiz. Bu fayl " + file.getYear() + " yil saqlangan");
        }

        if (!Objects.equals(file.getRegionId(), profile.getRegionId())) {
            throw new CustomException("Siz faqat o'z hududingizga tegishli file ni o'chira olasiz");
        }

        repository.delete(file);
    }
}
