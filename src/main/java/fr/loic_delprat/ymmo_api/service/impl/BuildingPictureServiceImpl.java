package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.BuildingPicture;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.BuildingPictureRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.service.BuildingPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingPictureServiceImpl implements BuildingPictureService {

    private static final String UPLOAD_DIR = "uploads/buildings/";

    private final BuildingPictureRepository buildingPictureRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public BuildingPictureResponse addPicture(Long buildingId, File picture) throws IOException {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

        Path uploadPath = Paths.get(UPLOAD_DIR + buildingId);
        Files.createDirectories(uploadPath);

        String originalName = picture.getName();
        String extension = originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";
        String storedName = UUID.randomUUID() + extension;

        Path destination = uploadPath.resolve(storedName);
        Files.copy(picture.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        String relativePath = UPLOAD_DIR + buildingId + "/" + storedName;

        BuildingPicture entity = BuildingPicture.builder()
                .path(relativePath)
                .building(building)
                .build();

        return mapToResponse(buildingPictureRepository.save(entity));
    }

    @Override
    public BuildingPictureResponse getPictureById(Long buildingId, Long id) {
        if (!buildingRepository.existsById(buildingId)) {
            throw new ResourceNotFoundException("Building not found with id: " + buildingId);
        }

        BuildingPicture picture = buildingPictureRepository.findByIdAndBuildingId(id, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Picture not found with id: " + id + " for building: " + buildingId));

        return mapToResponse(picture);
    }

    @Override
    public List<BuildingPictureResponse> getPicturesByBuilding(Long buildingId) {
        if (!buildingRepository.existsById(buildingId)) {
            throw new ResourceNotFoundException("Building not found with id: " + buildingId);
        }

        return buildingPictureRepository.findByBuildingId(buildingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deletePicture(Long buildingId, Long id) {
        BuildingPicture picture = buildingPictureRepository.findByIdAndBuildingId(id, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Picture not found with id: " + id + " for building: " + buildingId));

        File file = new File(picture.getPath());
        if (file.exists()) {
            file.delete();
        }

        buildingPictureRepository.delete(picture);
    }

    private BuildingPictureResponse mapToResponse(BuildingPicture picture) {
        String base64Data = null;
        String contentType = null;

        File file = new File(picture.getPath());
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                base64Data = Base64.getEncoder().encodeToString(bytes);
                contentType = Files.probeContentType(file.toPath());
                if (contentType == null) contentType = "application/octet-stream";
            } catch (IOException e) {
                // fichier illisible
            }
        }

        return BuildingPictureResponse.builder()
                .id(picture.getId())
                .path(picture.getPath())
                .buildingId(picture.getBuilding().getId())
                .contentType(contentType)
                .data(base64Data)
                .build();
    }
}