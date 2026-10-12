package com.miguelpires.wallet.service;

import com.miguelpires.wallet.entity.Account;
import com.miguelpires.wallet.entity.Transaction;
import com.miguelpires.wallet.repository.AccountRepository;
import com.miguelpires.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    //Attribute
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    //Constructor
    public TransactionService(TransactionRepository transaction, AccountRepository account){
        this.transactionRepository = transaction;
        this.accountRepository = account;
    }

    //Methods
    //Transactional - Em caso de erro, volta ao estado inicial
    @Transactional
    public Transaction createTransaction(UUID sourceID, UUID targetID, BigDecimal amount, String description){

        Account sourceAccount = accountRepository.findById(sourceID)
               .orElseThrow( () -> new IllegalArgumentException("Main account not found"));

        Account targetAccount = accountRepository.findById(targetID)
               .orElseThrow( () -> new IllegalArgumentException("Target account not found"));

        sourceAccount.debit(amount);
        targetAccount.credit(amount);

        Transaction newTransaction = new Transaction(sourceAccount, targetAccount, amount, description);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
        transactionRepository.save(newTransaction);

        return newTransaction;
    }

    @Transactional
    public List<Transaction> getAllTransaction(UUID id) {
        if (!accountRepository.existsById(id)) throw new IllegalArgumentException("Main account not found");

        List<Transaction> allTransactions = transactionRepository.findAllTransactions(id);
        return allTransactions;
    }

    @Transactional
    public List<Transaction> getTransactionsSent(UUID id) {
        if (!accountRepository.existsById(id)) throw new IllegalArgumentException("Main account not found");

        List<Transaction> allTransactions = transactionRepository.findAllBySourceAccount_IdAndTimestampOrderByTimestampDesc(id);
        return allTransactions;
    }

    @Transactional
    public List<Transaction> getTransactionReceived(UUID id) {
        if (!accountRepository.existsById(id)) throw new IllegalArgumentException("Main account not found");
        
        List<Transaction> allTransactions = transactionRepository.findAllByTargetAccount_IdAndTimestampOrderByTimestampDesc(id);
        return allTransactions;
    }
}
