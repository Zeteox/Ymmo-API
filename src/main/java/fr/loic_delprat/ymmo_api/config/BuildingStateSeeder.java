package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.BuildingState;
import fr.loic_delprat.ymmo_api.entity.BuildingStateName;
import fr.loic_delprat.ymmo_api.entity.BuildingTypeName;
import fr.loic_delprat.ymmo_api.repository.BuildingStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class BuildingStateSeeder implements CommandLineRunner {
    private final BuildingStateRepository buildingStateRepository;
    @Override
    public void run(String... args) throws Exception {
        for (BuildingStateName name : BuildingStateName.values()) {
            seedBuildingState(name);
        }
    }

    private void seedBuildingState(BuildingStateName name) {
        buildingStateRepository.findByName(name)
                .orElseGet(() -> buildingStateRepository.save(
                        BuildingState.builder().name(name).build()
                ));
    }
}
