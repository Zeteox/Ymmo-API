package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.CreateContactDemandRequest;
import fr.loic_delprat.ymmo_api.dto.response.ContactDemandResponse;

import java.util.List;

public interface ContactDemandService {
    ContactDemandResponse addDemand(Long buildingId, CreateContactDemandRequest request);
    ContactDemandResponse getDemandById(Long buildingId, Long id);
    List<ContactDemandResponse> getDemandsByBuilding(Long buildingId);
    void deleteDemand(Long buildingId, Long id);
}
