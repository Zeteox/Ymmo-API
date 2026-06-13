package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.BuildingState;
import fr.loic_delprat.ymmo_api.entity.BuildingStateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingStateRepository extends JpaRepository<BuildingState, Long> {
    Optional<BuildingState> findByName(BuildingStateName name);
}
