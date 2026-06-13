package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Optional<Agency> findByName(String name);
}
