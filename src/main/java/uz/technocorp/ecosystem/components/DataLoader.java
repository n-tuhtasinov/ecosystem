package uz.technocorp.ecosystem.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.enums.Role;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description any commands based on initial mode's value. It is usually needed to run the project at the first time
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            userRepository.save(User.builder().username("superadmin").password("root1234").role(Role.ADMIN).name("Super Admin").enabled(true).build());
        }
    }
}
