package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.BuildingType;
import fr.loic_delprat.ymmo_api.entity.BuildingTypeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {
    Optional<BuildingType> findByName(BuildingTypeName name);
}
