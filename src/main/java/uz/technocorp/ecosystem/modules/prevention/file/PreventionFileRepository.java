package uz.technocorp.ecosystem.modules.prevention.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Suxrob
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@Repository
public interface PreventionFileRepository extends JpaRepository<PreventionFile, String>, JpaSpecificationExecutor<PreventionFile> {

    Optional<PreventionFile> findByYearAndRegionId(Integer year, Integer regionId);

    Optional<PreventionFile> findByPath(String filePath);
}
