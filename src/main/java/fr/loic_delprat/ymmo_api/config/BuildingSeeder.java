package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.*;
import fr.loic_delprat.ymmo_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Order(3)
@RequiredArgsConstructor
public class BuildingSeeder implements CommandLineRunner {

    private final BuildingRepository buildingRepository;
    private final AgencyRepository agencyRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final BuildingStateRepository buildingStateRepository;
    private final ZoneRepository zoneRepository;

    private static final int TARGET_COUNT = 300;

    @Override
    public void run(String... args) throws Exception {
        long existing = buildingRepository.count();
        if (existing >= TARGET_COUNT) return;

        List<Agency>        agencies = agencyRepository.findAll();
        List<BuildingType>  types    = buildingTypeRepository.findAll();
        List<BuildingState> states   = buildingStateRepository.findAll();
        List<Zone>          zones    = zoneRepository.findAll();

        Random random = new Random();
        long toCreate = TARGET_COUNT - existing;

        for (int i = 0; i < toCreate; i++) {
            int index = (int) (existing + i + 1);
            buildingRepository.save(
                Building.builder()
                    .name("Building" + index)
                    .price(randomPrice(random))
                    .address(index + " Rue de la Paix")
                    .description("Description du bien numéro " + index)
                    .agency(randomFrom(agencies, random))
                    .buildingType(randomFrom(types, random))
                    .buildingState(randomFrom(states, random))
                    .zone(randomFrom(zones, random))
                    .build()
            );
        }

        System.out.println("[BuildingSeeder] " + toCreate + " buildings créés.");
    }

    private float randomPrice(Random random) {
        // Prix entre 50 000 et 950 000
        return 50_000f + random.nextInt(900_000);
    }

    private <T> T randomFrom(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
