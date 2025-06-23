package uz.technocorp.ecosystem.modules.profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, UUID>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByTin(Long tin);

    @Query(value = """
            select id,
                legal_name as legalName,
                legal_address as legalAddress,
                full_name as fullName,
                tin,
                pin,
                region_id as regionId,
                region_name as regionName,
                district_id as districtId,
                district_name as districtName,
                phone_number as phoneNumber
                from profile
                where tin = :tin
            """, nativeQuery = true)
    Optional<ProfileInfoView> getProfileByTin(Long tin);

    boolean existsByTin(Long tin);

}
