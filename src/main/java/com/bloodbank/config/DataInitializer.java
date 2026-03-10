package com.bloodbank.config;

import com.bloodbank.entity.User;
import com.bloodbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Ensures the default admin user exists on every startup.
 * This is a safety net on top of schema.sql INSERT IGNORE.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        var existing = userRepository.findByEmail("admin@bloodbank.com");
        if (existing.isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@bloodbank.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(admin);
            log.info("Default admin user created: admin@bloodbank.com / admin123");
        } else {
            // Ensure the password is valid (fix for bad hash from old schema.sql)
            User admin = existing.get();
            if (!passwordEncoder.matches("admin123", admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode("admin123"));
                userRepository.save(admin);
                log.info("Admin password was reset to default: admin123");
            }
        }
    }
}
