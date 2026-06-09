package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;
import fr.loic_delprat.ymmo_api.service.UserFavoriteBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
@RequiredArgsConstructor
@CrossOrigin
public class UserFavoriteBuildingController {

    private final UserFavoriteBuildingService userFavoriteBuildingService;

    @PostMapping("/{buildingId}")
    public ResponseEntity<Void> addFavorite(
            @PathVariable Long userId,
            @PathVariable Long buildingId) {
        userFavoriteBuildingService.addFavorite(userId, buildingId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{buildingId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long userId,
            @PathVariable Long buildingId) {
        userFavoriteBuildingService.removeFavorite(userId, buildingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getFavoritesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userFavoriteBuildingService.getFavoritesByUser(userId));
    }
}
