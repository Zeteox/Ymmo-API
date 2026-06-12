package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.response.BuildingPictureResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BuildingPictureService {
    BuildingPictureResponse addPicture(Long buildingId, File picture) throws IOException;
    BuildingPictureResponse getPictureById(Long buildingId, Long id);
    List<BuildingPictureResponse> getPicturesByBuilding(Long buildingId);
    void deletePicture(Long buildingId, Long id);

}
