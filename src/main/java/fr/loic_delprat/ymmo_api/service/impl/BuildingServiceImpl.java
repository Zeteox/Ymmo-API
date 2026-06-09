package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.CreateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.request.UpdateBuildingRequest;
import fr.loic_delprat.ymmo_api.dto.response.BuildingResponse;
import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.BuildingState;
import fr.loic_delprat.ymmo_api.entity.BuildingType;
import fr.loic_delprat.ymmo_api.entity.Zone;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingStateRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingTypeRepository;
import fr.loic_delprat.ymmo_api.repository.ZoneRepository;
import fr.loic_delprat.ymmo_api.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final AgencyRepository agencyRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final BuildingStateRepository buildingStateRepository;
    private final ZoneRepository zoneRepository;

    @Override
    public BuildingResponse createBuilding(CreateBuildingRequest request) {
        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + request.getAgencyId()));

        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("BuildingType not found with id: " + request.getBuildingTypeId()));

        BuildingState buildingState = buildingStateRepository.findById(request.getBuildingStateId())
                .orElseThrow(() -> new ResourceNotFoundException("BuildingState not found with id: " + request.getBuildingStateId()));

        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + request.getZoneId()));

        Building building = Building.builder()
                .name(request.getName())
                .price(request.getPrice())
                .address(request.getAddress())
                .description(request.getDescription())
                .agency(agency)
                .buildingType(buildingType)
                .buildingState(buildingState)
                .zone(zone)
                .build();

        return mapToResponse(buildingRepository.save(building));
    }

    @Override
    public BuildingResponse getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + id));

        return mapToResponse(building);
    }

    @Override
    public List<BuildingResponse> getAllBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BuildingResponse updateBuilding(Long id, UpdateBuildingRequest request) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + id));

        if (request.getName() != null) building.setName(request.getName());
        if (request.getPrice() != null) building.setPrice(request.getPrice());
        if (request.getAddress() != null) building.setAddress(request.getAddress());
        if (request.getDescription() != null) building.setDescription(request.getDescription());

        if (request.getAgencyId() != null) {
            Agency agency = agencyRepository.findById(request.getAgencyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + request.getAgencyId()));
            building.setAgency(agency);
        }

        if (request.getBuildingTypeId() != null) {
            BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("BuildingType not found with id: " + request.getBuildingTypeId()));
            building.setBuildingType(buildingType);
        }

        if (request.getBuildingStateId() != null) {
            BuildingState buildingState = buildingStateRepository.findById(request.getBuildingStateId())
                    .orElseThrow(() -> new ResourceNotFoundException("BuildingState not found with id: " + request.getBuildingStateId()));
            building.setBuildingState(buildingState);
        }

        if (request.getZoneId() != null) {
            Zone zone = zoneRepository.findById(request.getZoneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + request.getZoneId()));
            building.setZone(zone);
        }

        return mapToResponse(buildingRepository.save(building));
    }

    @Override
    public void deleteBuilding(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Building not found with id: " + id);
        }
        buildingRepository.deleteById(id);
    }

    private BuildingResponse mapToResponse(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .price(building.getPrice())
                .address(building.getAddress())
                .description(building.getDescription())
                .type(building.getBuildingType().getName().name())
                .state(building.getBuildingState().getName().name())
                .zone(building.getZone().getName().name())
                .build();
    }
}