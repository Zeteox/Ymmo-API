package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.Role;
import fr.loic_delprat.ymmo_api.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
