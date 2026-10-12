package com.miguelpires.wallet.repository;

import com.miguelpires.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    LocalDateTime date = LocalDateTime.now();

    @Query("select t from Transaction t where t.sourceAccount = :id or t.targetAccount = :id order by t.timestamp desc")
    List<Transaction> findAllTransactions(@Param("id") UUID id);

    List<Transaction> findAllBySourceAccount_IdAndTimestampOrderByTimestampDesc (UUID sourceAccountId);
    List<Transaction> findAllByTargetAccount_IdAndTimestampOrderByTimestampDesc(UUID targetAccountId);
}
