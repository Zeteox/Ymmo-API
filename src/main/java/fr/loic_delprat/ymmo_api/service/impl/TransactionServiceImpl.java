package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.CreateTransactionRequest;
import fr.loic_delprat.ymmo_api.dto.request.UpdateTransactionRequest;
import fr.loic_delprat.ymmo_api.dto.response.TransactionResponse;
import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.Building;
import fr.loic_delprat.ymmo_api.entity.Transaction;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.BuildingRepository;
import fr.loic_delprat.ymmo_api.repository.TransactionRepository;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import fr.loic_delprat.ymmo_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + request.getBuildingId()));

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id: " + request.getBuyerId()));

        User agent = userRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + request.getAgentId()));

        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + request.getAgencyId()));

        Transaction transaction = Transaction.builder()
                .building(building)
                .buyer(buyer)
                .agent(agent)
                .agency(agency)
                .amount(request.getAmount())
                .build();

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        return mapToResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TransactionResponse updateTransaction(Long id, UpdateTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        if (request.getAmount() != null) transaction.setAmount(request.getAmount());

        if (request.getBuildingId() != null) {
            Building building = buildingRepository.findById(request.getBuildingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Building not found with id: " + request.getBuildingId()));
            transaction.setBuilding(building);
        }

        if (request.getBuyerId() != null) {
            User buyer = userRepository.findById(request.getBuyerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id: " + request.getBuyerId()));
            transaction.setBuyer(buyer);
        }

        if (request.getAgentId() != null) {
            User agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + request.getAgentId()));
            transaction.setAgent(agent);
        }

        if (request.getAgencyId() != null) {
            Agency agency = agencyRepository.findById(request.getAgencyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + request.getAgencyId()));
            transaction.setAgency(agency);
        }

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .buildingId(transaction.getBuilding().getId())
                .buildingName(transaction.getBuilding().getName())
                .buyerId(transaction.getBuyer().getId())
                .buyerFullName(transaction.getBuyer().getFirstName() + " " + transaction.getBuyer().getLastName())
                .agentId(transaction.getAgent().getId())
                .agentFullName(transaction.getAgent().getFirstName() + " " + transaction.getAgent().getLastName())
                .agencyId(transaction.getAgency().getId())
                .agencyName(transaction.getAgency().getName())
                .amount(transaction.getAmount())
                .date(transaction.getCreatedAt().toString())
                .build();
    }
}
