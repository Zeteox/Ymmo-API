package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.ContactDemand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactDemandRepository extends JpaRepository<ContactDemand, Long> {
    List<ContactDemand> findByBuildingId(Long buildingId);
    Optional<ContactDemand> findByIdAndBuildingId(Long id, Long buildingId);
}
