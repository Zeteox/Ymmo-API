package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.*;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.CityRepository;
import fr.loic_delprat.ymmo_api.repository.RoleRepository;
import fr.loic_delprat.ymmo_api.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class AgencySeeder implements CommandLineRunner {
    private final AgencyRepository agencyRepository;
    private final CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        for (CityName city : CityName.values()) {
            seedAgency("Agence " + city.toString(), city);
        }
    }

    private void seedAgency(String name, CityName cityName) {
        City city = cityRepository.findByName(cityName)
                        .orElseThrow(() -> new RuntimeException("City " + cityName + " not found."));

        agencyRepository.findByName(name)
                .orElseGet(() -> agencyRepository.save(
                        Agency.builder()
                                .name(name)
                                .city(city)
                                .build()
                ));
    }
}
