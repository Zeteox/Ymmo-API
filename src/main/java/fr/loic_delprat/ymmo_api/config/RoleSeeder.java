package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.Role;
import fr.loic_delprat.ymmo_api.entity.RoleName;
import fr.loic_delprat.ymmo_api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        seedRole(RoleName.ROLE_AGENT);
        seedRole(RoleName.ROLE_USER);
    }

    private void seedRole(RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                    Role.builder().name(roleName).build()
                ));
    }
}
