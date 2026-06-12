package fr.loic_delprat.ymmo_api.controller;

import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;
import fr.loic_delprat.ymmo_api.service.BuildingPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/buildings/{buildingId}/pictures")
@RequiredArgsConstructor
public class BuildingPictureController {

    private final BuildingPictureService buildingPictureService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<BuildingPictureResponse> addPicture(
            @PathVariable Long buildingId,
            @RequestParam("picture") MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile("upload_", ".tmp");
        try {
            multipartFile.transferTo(tempFile.toPath());
            BuildingPictureResponse response = buildingPictureService.addPicture(buildingId, tempFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } finally {
            tempFile.delete();
        }
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
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<Void> deletePicture(
            @PathVariable Long buildingId,
            @PathVariable Long id) {
        buildingPictureService.deletePicture(buildingId, id);
        return ResponseEntity.noContent().build();
    }
}