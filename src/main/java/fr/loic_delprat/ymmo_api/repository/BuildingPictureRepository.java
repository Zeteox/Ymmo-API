package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.BuildingPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingPictureRepository extends JpaRepository<BuildingPicture, Long> {
    List<BuildingPicture> findByBuildingId(Long buildingId);
    Optional<BuildingPicture> findByIdAndBuildingId(Long id, Long buildingId);
}
