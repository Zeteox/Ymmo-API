package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.*;
import fr.loic_delprat.ymmo_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Order(4)
@RequiredArgsConstructor
public class FavoriteSeeder implements CommandLineRunner {

    private final UserFavoriteBuildingRepository userFavoriteBuildingRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    private static final int MIN_FAVORITES = 55;
    private static final int MAX_FAVORITES = 205;

    @Override
    public void run(String... args) throws Exception {
        if (userFavoriteBuildingRepository.count() > 0) return;

        List<User>     users     = userRepository.findAll();
        List<Building> buildings = buildingRepository.findAll();

        if (users.isEmpty() || buildings.isEmpty()) return;

        Random random  = new Random();
        int    target  = MIN_FAVORITES + random.nextInt(MAX_FAVORITES - MIN_FAVORITES + 1);
        int    created = 0;

        // Génère toutes les paires possibles et mélange pour éviter les doublons
        List<long[]> pairs = new ArrayList<>();
        for (User u : users) {
            for (Building b : buildings) {
                pairs.add(new long[]{u.getId(), b.getId()});
            }
        }
        Collections.shuffle(pairs, random);

        for (long[] pair : pairs) {
            if (created >= target) break;

            User     user     = users.stream().filter(u -> u.getId().equals(pair[0])).findFirst().orElse(null);
            Building building = buildings.stream().filter(b -> b.getId().equals(pair[1])).findFirst().orElse(null);
            if (user == null || building == null) continue;

            userFavoriteBuildingRepository.save(
                UserFavoriteBuilding.builder()
                    .user(user)
                    .building(building)
                    .build()
            );
            created++;
        }

        System.out.println("[FavoriteSeeder] " + created + " favoris créés.");
    }
}
