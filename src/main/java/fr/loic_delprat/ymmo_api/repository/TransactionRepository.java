package fr.loic_delprat.ymmo_api.repository;

import fr.loic_delprat.ymmo_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBuyerId(Long buyerId);
    List<Transaction> findByAgentId(Long agentId);
    List<Transaction> findByAgencyId(Long agencyId);
    List<Transaction> findByBuildingId(Long buildingId);
}
