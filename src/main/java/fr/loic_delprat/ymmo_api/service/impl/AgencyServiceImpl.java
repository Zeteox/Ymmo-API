package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.AgencyRequest;
import fr.loic_delprat.ymmo_api.dto.response.AgencyResponse;
import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.City;
import fr.loic_delprat.ymmo_api.exeption.BadRequestException;
import fr.loic_delprat.ymmo_api.exeption.ConflictException;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.CityRepository;
import fr.loic_delprat.ymmo_api.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;
    private final CityRepository cityRepository;

    @Override
    public AgencyResponse createAgency(AgencyRequest request) {
        if (agencyRepository.findByName(request.getName()).isPresent()) {
            throw new ConflictException("Agency with name '" + request.getName() + "' already exists");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new BadRequestException("City no found with id" +  request.getCityId()));

        Agency agency = Agency.builder()
                .name(request.getName())
                .city(city)
                .build();

        Agency savedAgency = agencyRepository.save(agency);

        return mapToResponse(savedAgency);
    }

    @Override
    public AgencyResponse getAgencyById(Long id) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + id));

        return mapToResponse(agency);
    }

    @Override
    public List<AgencyResponse> getAllAgencies() {
        return agencyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AgencyResponse updateAgency(Long id, AgencyRequest request) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + id));

        if (!agency.getName().equals(request.getName()) && 
            agencyRepository.findByName(request.getName()).isPresent()) {
            throw new ConflictException("Agency with name '" + request.getName() + "' already exists");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new BadRequestException("City no found with id" +  request.getCityId()));

        agency.setName(request.getName());
        agency.setCity(city);

        Agency updatedAgency = agencyRepository.save(agency);

        return mapToResponse(updatedAgency);
    }

    @Override
    public void deleteAgency(Long id) {
        if (!agencyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agency not found with id: " + id);
        }

        agencyRepository.deleteById(id);
    }

    private AgencyResponse mapToResponse(Agency agency) {
        return AgencyResponse.builder()
                .id(agency.getId())
                .name(agency.getName())
                .city(agency.getCity().getName().name())
                .build();
    }
}

