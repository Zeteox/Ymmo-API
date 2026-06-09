package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingPictureRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.BuildingPicture;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.BuildingPictureRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.service.BuildingPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingPictureServiceImpl implements BuildingPictureService {

    private final BuildingPictureRepository buildingPictureRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public BuildingPictureResponse addPicture(Long buildingId, CreateBuildingPictureRequest request) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));

        BuildingPicture picture = BuildingPicture.builder()
                .path(request.getPath())
                .building(building)
                .build();

        return mapToResponse(buildingPictureRepository.save(picture));
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

        buildingPictureRepository.delete(picture);
    }

    private BuildingPictureResponse mapToResponse(BuildingPicture picture) {
        return BuildingPictureResponse.builder()
                .id(picture.getId())
                .path(picture.getPath())
                .buildingId(picture.getBuilding().getId())
                .build();
    }
}
