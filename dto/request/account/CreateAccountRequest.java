package com.miguelpires.wallet.dto.request.account;

import java.math.BigDecimal;

public record CreateAccountRequest(String holderName, BigDecimal initialBalance){}


