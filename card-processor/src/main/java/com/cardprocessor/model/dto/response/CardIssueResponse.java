package com.cardprocessor.model.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CardIssueResponse {

    private String cardId;
    private String barcode;
    private String userId;
    private String lastFour;
    private String status;
    private Instant createdAt;
    private Instant activationDate;
    private AccountResponse account;

}
