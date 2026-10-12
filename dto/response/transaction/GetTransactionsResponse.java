package com.miguelpires.wallet.dto.response.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetTransactionsResponse(UUID sourceId, UUID targetId, BigDecimal amount, LocalDateTime timestamp) {}
