package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.BuildingType;
import fr.loic_delprat.ymmo_api.entity.BuildingTypeName;
import fr.loic_delprat.ymmo_api.entity.CityName;
import fr.loic_delprat.ymmo_api.repository.BuildingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class BuildingTypeSeeder implements CommandLineRunner {
    private final BuildingTypeRepository buildingTypeRepository;
    @Override
    public void run(String... args) throws Exception {
        for (BuildingTypeName name : BuildingTypeName.values()) {
            seedBuildingType(name);
        }
    }

    private void seedBuildingType(BuildingTypeName name) {
        buildingTypeRepository.findByName(name)
                .orElseGet(() -> buildingTypeRepository.save(
                        BuildingType.builder().name(name).build()
                ));
    }
}
