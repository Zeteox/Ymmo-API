package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.BuildingTypeName;
import fr.loic_delprat.ymmo_api.entity.Zone;
import fr.loic_delprat.ymmo_api.entity.ZoneName;
import fr.loic_delprat.ymmo_api.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class ZoneSeeder implements CommandLineRunner {
    private final ZoneRepository zoneRepository;
    @Override
    public void run(String... args) throws Exception {
        for (ZoneName name : ZoneName.values()) {
            seedZone(name);
        }
    }

    private void seedZone(ZoneName zoneName) {
        zoneRepository.findByName(zoneName)
                .orElseGet(() -> zoneRepository.save(
                    Zone.builder().name(zoneName).build()
                ));
    }
}
