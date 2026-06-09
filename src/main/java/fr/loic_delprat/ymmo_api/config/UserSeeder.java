package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.Role;
import fr.loic_delprat.ymmo_api.entity.RoleName;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.RoleRepository;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AgencyRepository agencyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUser("Default", "Agent", "defaultagent@gmail.com", "0100000000", "default", RoleName.ROLE_AGENT, 1L);
        seedUser("Default", "User", "defaultuser@gmail.com", "0100200000", "default", RoleName.ROLE_USER, 1L);
    }

    private void seedUser(String firstName, String lastName, String email, String phone, String rawPassword, RoleName roleName, Long agencyId) {
        if (userRepository.existsByEmail(email) || userRepository.existsByPhone(phone)) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        Agency agency = agencyRepository.findById(agencyId)
                        .orElseThrow(() -> new RuntimeException("Agency not found: " + agencyId));

        userRepository.save(
                User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(phone)
                        .password(passwordEncoder.encode(rawPassword))
                        .role(role)
                        .agency(agency)
                        .isActive(true)
                        .build()
        );
    }
}
