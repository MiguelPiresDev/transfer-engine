package com.miguelpires.wallet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
public class Account {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    private BigDecimal balance;

    //Properties

    //contructor
    public Account(String name, BigDecimal bal){
        if (name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException("Invalid Name");
        }
        validateAmount(bal);

        this.holderName = name;
        this.balance = bal;
    }

    //Methods
    public boolean debit(BigDecimal amount) {
        validateAmount(amount);
        if (this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Debit Denied");
        }

        this.balance = this.balance.subtract(amount);
        return true;
    }

    public boolean credit(BigDecimal amount){
        validateAmount(amount);
        this.balance = this.balance.add(amount);
        return true;
    }

    private void validateAmount( BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid Amount");
        }
    }
}
