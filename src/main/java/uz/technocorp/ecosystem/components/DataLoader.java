package uz.technocorp.ecosystem.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description any commands based on initial mode's value. It is usually needed to run the project at the first time
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final OfficeRepository officeRepository;
    private final ProfileRepository profileRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;



    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            // save an admin
            userRepository.save(User.builder().username("superadmin").password(passwordEncoder.encode("root1234")).role(Role.ADMIN).name("Super Admin").enabled(true).build());

            // save region
            Region region1 = regionRepository.save(Region.builder().name("Toshkent shahri").number(25682).soato(1721).build());

            // save district
            District district1 = districtRepository.save(District.builder().name("Shayhontoxur tumani").soato(17222564).number(256585).regionId(region1.getId()).build());

            // save office
            Office office = officeRepository.save(Office.builder().name("Toshkent shahri bo'linmasi").build());

            // set office to the region
            region1.setOfficeId(office.getId());
            regionRepository.save(region1);

            // save a legal
            Profile legal1 = profileRepository.save(Profile.builder().tin(123654987L).legalName("Shaffof mchj").regionId(region1.getId()).districtId(district1.getId()).regionName(region1.getName()).districtName(district1.getName()).legalAddress("Ibn sino ko'chasi 48").build());
            userRepository.save(User.builder().username("tashkilot").password(passwordEncoder.encode("root1234")).role(Role.LEGAL).directions(List.of("APPEAL")).profileId(legal1.getId()).name("Shaffof mchj").enabled(true).build());

        }
    }
}
