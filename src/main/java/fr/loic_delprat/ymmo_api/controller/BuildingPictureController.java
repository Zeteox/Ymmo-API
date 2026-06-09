package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingPictureRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;
import fr.loic_delprat.ymmo_api.service.BuildingPictureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings/{buildingId}/pictures")
@RequiredArgsConstructor
@CrossOrigin
public class BuildingPictureController {

    private final BuildingPictureService buildingPictureService;

    @PostMapping
    public ResponseEntity<BuildingPictureResponse> addPicture(
            @PathVariable Long buildingId,
            @Valid @RequestBody CreateBuildingPictureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingPictureService.addPicture(buildingId, request));
    }

    @GetMapping
    public ResponseEntity<List<BuildingPictureResponse>> getPicturesByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(buildingPictureService.getPicturesByBuilding(buildingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingPictureResponse> getPictureById(
            @PathVariable Long buildingId,
            @PathVariable Long id) {
        return ResponseEntity.ok(buildingPictureService.getPictureById(buildingId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePicture(
            @PathVariable Long buildingId,
            @PathVariable Long id) {
        buildingPictureService.deletePicture(buildingId, id);
        return ResponseEntity.noContent().build();
    }
}
