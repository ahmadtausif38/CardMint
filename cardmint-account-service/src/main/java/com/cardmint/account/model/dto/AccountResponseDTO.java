package com.cardmint.account.model.dto;




import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountResponseDTO(
        String accountId,
        String currency,
        BigDecimal balance,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}