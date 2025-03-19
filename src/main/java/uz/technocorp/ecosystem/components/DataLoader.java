package uz.technocorp.ecosystem.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            userRepository.save(User.builder().username("superadmin").password(passwordEncoder.encode("root1234")).role(Role.ADMIN).name("Super Admin").enabled(true).build());
        }
    }
}
