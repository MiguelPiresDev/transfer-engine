package com.miguelpires.wallet.repository;

import com.miguelpires.wallet.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select Account WHERE Account.balance < 100")
    List<Account> findAccountWithLowBalance();

    void removeAccountById(UUID id);

    Account getAccountById(UUID id);
}
