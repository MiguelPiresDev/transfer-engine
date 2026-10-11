package com.miguelpires.wallet.controller;

import com.miguelpires.wallet.dto.request.transaction.CreateTransactionRequest;
import com.miguelpires.wallet.entity.Transaction;
import com.miguelpires.wallet.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/Transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest request){

       Transaction transaction = transactionService.createTransaction(
                request.sourceId(),
                request.targetId(),
                request.amount(),
                request.desc()
       );
       return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
