package com.miguelpires.wallet.dto.request.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(UUID sourceId, UUID targetId, BigDecimal amount, String desc) {
}
