package uz.technocorp.ecosystem.modules.prevention.file;

import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
public interface PreventionFileService {

    PreventionFile get(User user, Integer year);

    PreventionFile findByYearAndRegion(Integer year, Integer regionId);

    void create(User user, String filePath);

    void delete(User user, String filePath);
}
