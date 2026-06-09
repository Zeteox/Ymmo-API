package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.entity.UserFavoriteBuilding;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.repository.UserFavoriteBuildingRepository;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import fr.loic_delprat.ymmo_api.service.UserFavoriteBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFavoriteBuildingServiceImpl implements UserFavoriteBuildingService {

    private final UserFavoriteBuildingRepository userFavoriteBuildingRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public void addFavorite(Long userId, Long buildingId) {
        if (userFavoriteBuildingRepository.existsByUserIdAndBuildingId(userId, buildingId)) {
            return; // already a favorite, idempotent
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

        UserFavoriteBuilding favorite = UserFavoriteBuilding.builder()
                .user(user)
                .building(building)
                .build();

        userFavoriteBuildingRepository.save(favorite);
    }

    @Override
    public void removeFavorite(Long userId, Long buildingId) {
        UserFavoriteBuilding favorite = userFavoriteBuildingRepository.findByUserIdAndBuildingId(userId, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Favorite not found for user: " + userId + " and building: " + buildingId));

        userFavoriteBuildingRepository.delete(favorite);
    }

    @Override
    public List<BuildingResponse> getFavoritesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return userFavoriteBuildingRepository.findByUserId(userId)
                .stream()
                .map(fav -> mapToResponse(fav.getBuilding()))
                .toList();
    }

    private BuildingResponse mapToResponse(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .price(building.getPrice())
                .address(building.getAddress())
                .description(building.getDescription())
                .type(building.getBuildingType().getName().name())
                .state(building.getBuildingState().getName().name())
                .zone(building.getZone().getName().name())
                .build();
    }
}
