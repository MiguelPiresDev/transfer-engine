package com.miguelpires.wallet.controller;

import com.miguelpires.wallet.dto.request.account.CreateAccountRequest;
import com.miguelpires.wallet.dto.response.account.GetAccountResponse;
import com.miguelpires.wallet.entity.Account;
import com.miguelpires.wallet.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService service){
        this.accountService = service;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request){
        Account account = accountService.createAccount(
            request.holderName(),
            request.initialBalance()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<GetAccountResponse> getAccount(@PathVariable UUID id){
        Account account = accountService.getAccount(id);

        GetAccountResponse response = new GetAccountResponse(
                account.getId(),
                account.getHolderName(),
                account.getBalance()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<java.lang.String> deleteAccount(UUID id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }
}
