package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.request.UpdateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;
import fr.loic_delprat.ymmo_api.service.BuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<BuildingResponse> createBuilding(@Valid @RequestBody CreateBuildingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createBuilding(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getBuildingById(@PathVariable Long id) {
        return ResponseEntity.ok(buildingService.getBuildingById(id));
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<BuildingResponse>> getBuildingsByAgencyId(@PathVariable Long agencyId) {
        return ResponseEntity.ok(buildingService.getBuildingsByAgencyId(agencyId));
    }

    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<BuildingResponse> updateBuilding(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBuildingRequest request) {
        return ResponseEntity.ok(buildingService.updateBuilding(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}