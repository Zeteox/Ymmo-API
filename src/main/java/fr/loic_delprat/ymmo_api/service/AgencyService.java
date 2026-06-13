package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.AgencyRequest;
import fr.loic_delprat.ymmo_api.dto.response.AgencyResponse;

import java.util.List;

public interface AgencyService {
    AgencyResponse createAgency(AgencyRequest request);
    AgencyResponse getAgencyById(Long id);
    List<AgencyResponse> getAllAgencies();
    AgencyResponse updateAgency(Long id, AgencyRequest request);
    void deleteAgency(Long id);
}

