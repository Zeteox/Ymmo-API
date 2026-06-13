package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.City;
import fr.loic_delprat.ymmo_api.entity.CityName;
import fr.loic_delprat.ymmo_api.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class CitySeeder implements CommandLineRunner {
    private final CityRepository cityRepository;
    @Override
    public void run(String... args) throws Exception {
        for (CityName name : CityName.values()) {
            seedCity(name);
        }
    }

    private void seedCity(CityName cityName) {
        cityRepository.findByName(cityName)
                .orElseGet(() -> cityRepository.save(
                    City.builder().name(cityName).build()
                ));
    }
}
