package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;

import java.util.List;

public interface UserFavoriteBuildingService {
    void addFavorite(Long userId, Long buildingId);
    void removeFavorite(Long userId, Long buildingId);
    List<BuildingResponse> getFavoritesByUser(Long userId);
}
