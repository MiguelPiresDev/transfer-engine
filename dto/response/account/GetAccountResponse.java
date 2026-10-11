package com.miguelpires.wallet.dto.response.account;

import java.math.BigDecimal;
import java.util.UUID;

public record GetAccountResponse(UUID id, String holderName, BigDecimal initialBalance) {}
