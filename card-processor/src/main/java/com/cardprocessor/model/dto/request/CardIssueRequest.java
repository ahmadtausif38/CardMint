package com.cardprocessor.model.dto.request;

import lombok.Data;

@Data
public class CardIssueRequest {

    private String userId;
    private String cardId;
    private String barcode;

}
