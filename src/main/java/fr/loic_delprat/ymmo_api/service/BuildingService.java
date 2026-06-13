package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.request.UpdateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;

import java.util.List;

public interface BuildingService {
    BuildingResponse createBuilding(CreateBuildingRequest request);
    BuildingResponse getBuildingById(Long id);
    List<BuildingResponse> getAllBuildings();
    List<BuildingResponse> getBuildingsByAgencyId(Long agencyId);
    BuildingResponse updateBuilding(Long id, UpdateBuildingRequest request);
    void deleteBuilding(Long id);
}