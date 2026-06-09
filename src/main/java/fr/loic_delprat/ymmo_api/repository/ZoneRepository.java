package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.Zone;
import fr.loic_delprat.ymmo_api.entity.ZoneName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByName(ZoneName name);
}
