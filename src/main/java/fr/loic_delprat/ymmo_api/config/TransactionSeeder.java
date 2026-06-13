package fr.loic_delprat.ymmo_api.config;

import fr.loic_delprat.ymmo_api.entity.*;
import fr.loic_delprat.ymmo_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Order(5)
@RequiredArgsConstructor
public class TransactionSeeder implements CommandLineRunner {

    private final TransactionRepository transactionRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;

    private static final int MIN_TRANSACTIONS = 85;
    private static final int MAX_TRANSACTIONS = 200;

    @Override
    public void run(String... args) throws Exception {
        if (transactionRepository.count() > 0) return;

        List<Building> buildings = buildingRepository.findAll();
        List<User>     buyers    = userRepository.findAll().stream()
                                    .filter(u -> u.getRole().getName().toString().equals("ROLE_USER"))
                                    .collect(Collectors.toList());
        List<User>     agents    = userRepository.findAll().stream()
                                    .filter(u -> u.getRole().getName().toString().equals("ROLE_AGENT"))
                                    .collect(Collectors.toList());
        List<Agency>   agencies  = agencyRepository.findAll();

        if (buildings.isEmpty() || buyers.isEmpty() || agents.isEmpty()) return;

        Random random = new Random();
        int    target = MIN_TRANSACTIONS + random.nextInt(MAX_TRANSACTIONS - MIN_TRANSACTIONS + 1);

        // On évite de vendre deux fois le même building
        List<Building> available = new java.util.ArrayList<>(buildings);
        java.util.Collections.shuffle(available, random);

        int created = 0;
        for (Building building : available) {
            if (created >= target) break;

            User   buyer  = randomFrom(buyers, random);
            User   agent  = randomFrom(agents, random);
            Agency agency = randomFrom(agencies, random);

            // Montant légèrement varié autour du prix du bien (±10%)
            float variation = 0.9f + random.nextFloat() * 0.2f;
            float amount    = building.getPrice() * variation;

            transactionRepository.save(
                Transaction.builder()
                    .building(building)
                    .buyer(buyer)
                    .agent(agent)
                    .agency(agency)
                    .amount(amount)
                    .build()
            );
            created++;
        }

        System.out.println("[TransactionSeeder] " + created + " transactions créées.");
    }

    private <T> T randomFrom(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
