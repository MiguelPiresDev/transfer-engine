package com.miguelpires.wallet.controller;

import com.miguelpires.wallet.dto.request.transaction.CreateTransactionRequest;
import com.miguelpires.wallet.dto.response.transaction.GetTransactionsResponse;
import com.miguelpires.wallet.entity.Transaction;
import com.miguelpires.wallet.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest request){
       Transaction transaction = transactionService.createTransaction(
                request.sourceId(),
                request.targetId(),
                request.amount(),
                request.desc()
       );
       return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/myTransactions/{id}")
    public ResponseEntity<List<GetTransactionsResponse>> myTransactions (@PathVariable UUID id){
        List<Transaction> transactions = transactionService.getAllTransaction(id);

        List<GetTransactionsResponse> responses = transactions.stream()
                .map(transaction -> new GetTransactionsResponse(
                transaction.getSourceAccount().getId(),
                transaction.getTargetAccount().getId(),
                transaction.getAmount(),
                transaction.getTimestamp()
                )
        ).toList();
        return ResponseEntity.accepted().body(responses);
    }

    @GetMapping("/mySentTransactions/{id}")
    public ResponseEntity<List<GetTransactionsResponse>> mySentTransactions (@PathVariable UUID id){
        List<Transaction> transactions = transactionService.getTransactionsSent(id);

        List<GetTransactionsResponse> responses = transactions.stream()
                .map(tran-> new GetTransactionsResponse(
                        tran.getSourceAccount().getId(),
                        tran.getTargetAccount().getId(),
                        tran.getAmount(),
                        tran.getTimestamp()
                )
        ).toList();
        return ResponseEntity.accepted().body(responses);
    }

    @GetMapping("/myReceivedTransactions/{id}")
    public ResponseEntity<List<GetTransactionsResponse>> myReceivedTransactions (@PathVariable UUID id){
        List<Transaction> transactions = transactionService.getTransactionReceived(id);

        List<GetTransactionsResponse> responses = transactions.stream()
                .map(tran-> new GetTransactionsResponse(
                                tran.getSourceAccount().getId(),
                                tran.getTargetAccount().getId(),
                                tran.getAmount(),
                                tran.getTimestamp()
                        )
                ).toList();
        return ResponseEntity.accepted().body(responses);
    }
}
