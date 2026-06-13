package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.CreateContactDemandRequest;
import fr.loic_delprat.ymmo_api.dto.response.ContactDemandResponse;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.ContactDemand;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.repository.ContactDemandRepository;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import fr.loic_delprat.ymmo_api.service.ContactDemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactDemandServiceImpl implements ContactDemandService {

    private final ContactDemandRepository contactDemandRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;

    @Override
    public ContactDemandResponse addDemand(Long buildingId, CreateContactDemandRequest request) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + buildingId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        ContactDemand demand = ContactDemand.builder()
                .content(request.getContent())
                .user(user)
                .building(building)
                .build();

        return mapToResponse(contactDemandRepository.save(demand));
    }

    @Override
    public ContactDemandResponse getDemandById(Long buildingId, Long id) {
        if (!buildingRepository.existsById(buildingId)) {
            throw new ResourceNotFoundException("Building not found with id: " + buildingId);
        }

        ContactDemand demand = contactDemandRepository.findByIdAndBuildingId(id, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Demand not found with id: " + id + " for building: " + buildingId));

        return mapToResponse(demand);
    }

    @Override
    public List<ContactDemandResponse> getDemandsByBuilding(Long buildingId) {
        if (!buildingRepository.existsById(buildingId)) {
            throw new ResourceNotFoundException("Building not found with id: " + buildingId);
        }

        return contactDemandRepository.findByBuildingId(buildingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteDemand(Long buildingId, Long id) {
        ContactDemand demand = contactDemandRepository.findByIdAndBuildingId(id, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Demand not found with id: " + id + " for building: " + buildingId));

        contactDemandRepository.delete(demand);
    }

    private ContactDemandResponse mapToResponse(ContactDemand demand) {
        return ContactDemandResponse.builder()
                .id(demand.getId())
                .userId(demand.getUser().getId())
                .buildingId(demand.getBuilding().getId())
                .content(demand.getContent())
                .build();
    }
}
