package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.CityName;
import fr.loic_delprat.ymmo_api.entity.Role;
import fr.loic_delprat.ymmo_api.entity.RoleName;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class AgencySeeder implements CommandLineRunner {
    private final AgencyRepository agencyRepository;

    @Override
    public void run(String... args) throws Exception {
        for (CityName city : CityName.values()) {
            seedAgency("Agence " + city.toString(), city);
        }
    }

    private void seedAgency(String name, CityName cityName) {
        agencyRepository.findByName(name)
                .orElseGet(() -> agencyRepository.save(
                        Agency.builder()
                                .name(name)
                                .city(cityName)
                                .build()
                ));
    }
}
