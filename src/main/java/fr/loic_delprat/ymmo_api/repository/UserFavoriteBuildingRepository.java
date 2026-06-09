package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.UserFavoriteBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteBuildingRepository extends JpaRepository<UserFavoriteBuilding, Long> {
    List<UserFavoriteBuilding> findByUserId(Long userId);
    Optional<UserFavoriteBuilding> findByUserIdAndBuildingId(Long userId, Long buildingId);
    boolean existsByUserIdAndBuildingId(Long userId, Long buildingId);
}
