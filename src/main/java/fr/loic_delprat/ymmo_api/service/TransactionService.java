package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.CreateTransactionRequest;
import fr.loic_delprat.ymmo_api.dto.request.UpdateTransactionRequest;
import fr.loic_delprat.ymmo_api.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest request);
    TransactionResponse getTransactionById(Long id);
    List<TransactionResponse> getAllTransactions();
    TransactionResponse updateTransaction(Long id, UpdateTransactionRequest request);
    void deleteTransaction(Long id);
}
