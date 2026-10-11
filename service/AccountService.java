package com.miguelpires.wallet.service;

import com.miguelpires.wallet.entity.Account;
import com.miguelpires.wallet.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository account){
        this.accountRepository = account;
    }

    @Transactional
    public Account createAccount(String holderName, BigDecimal initialAmount){
        if (initialAmount.compareTo(new BigDecimal(100)) < 0)
            throw new IllegalArgumentException("Inicial amount value not enough");

        Account account = new Account(holderName, initialAmount);
        accountRepository.save(account);
        return account;
    }

    @Transactional
    public Account getAccount(UUID id){
        Account account = accountRepository.getAccountById(id);
        if (account == null) throw new IllegalArgumentException("Account not found");

        return  account;
    }

    @Transactional
    public boolean deleteAccount(UUID accountID){
       if(!accountRepository.existsById(accountID))
           throw new IllegalArgumentException("Account not found");
 
       accountRepository.deleteById(accountID);
       return true;
    }
}
