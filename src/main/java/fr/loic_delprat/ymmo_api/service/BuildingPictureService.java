package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingPictureRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;

import java.util.List;

public interface BuildingPictureService {
    BuildingPictureResponse addPicture(Long buildingId, CreateBuildingPictureRequest request);
    BuildingPictureResponse getPictureById(Long buildingId, Long id);
    List<BuildingPictureResponse> getPicturesByBuilding(Long buildingId);
    void deletePicture(Long buildingId, Long id);
}
