package com.cardprocessor.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class AccountResponse {

    private String accountId;
    private String userId;
    private BigDecimal balance;
    private String status;
    private String currency;
    private Instant createdAt;

}
