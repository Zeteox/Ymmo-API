package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.request.CreateContactDemandRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;
import fr.loic_delprat.ymmo_api.dto.response.ContactDemandResponse;
import fr.loic_delprat.ymmo_api.service.BuildingPictureService;
import fr.loic_delprat.ymmo_api.service.ContactDemandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings/{buildingId}/demands")
@RequiredArgsConstructor
public class ContactDemandController {

    private final ContactDemandService contactDemandService;

    @PostMapping
    public ResponseEntity<ContactDemandResponse> addDemand(
            @PathVariable Long buildingId,
            @Valid @RequestBody CreateContactDemandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactDemandService.addDemand(buildingId, request));
    }

    @GetMapping
    public ResponseEntity<List<ContactDemandResponse>> getDemandsByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(contactDemandService.getDemandsByBuilding(buildingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDemandResponse> getDemandById(
            @PathVariable Long buildingId,
            @PathVariable Long id) {
        return ResponseEntity.ok(contactDemandService.getDemandById(buildingId, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<Void> deleteDemand(
            @PathVariable Long buildingId,
            @PathVariable Long id) {
        contactDemandService.deleteDemand(buildingId, id);
        return ResponseEntity.noContent().build();
    }
}
