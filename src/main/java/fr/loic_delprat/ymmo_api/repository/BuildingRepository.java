package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByAgencyId(Long agencyId);
    List<Building> findByZoneId(Long zoneId);
    List<Building> findByBuildingTypeId(Long buildingTypeId);
    List<Building> findByBuildingStateId(Long buildingStateId);
}