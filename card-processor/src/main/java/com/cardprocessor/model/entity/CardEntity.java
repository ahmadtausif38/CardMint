package com.cardprocessor.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cardId;
    private String barcode;
    private String userId;
    private String accountId;
    private String cardNumber;
    private String cvv;
    private String expiry;
    private String lastFour;
    private String status;
    private Instant createdAt;
    private Instant activationDate;

    // Constructor for seed data
    public CardEntity(String cardId, String barcode, String status, Instant createdAt,
                String cardNumber, String cvv, String expiry) {
        this.cardId = cardId;
        this.barcode = barcode;
        this.status = status;
        this.createdAt = createdAt;
        this.activationDate = null;
        this.userId = null;
        this.accountId = null;
        this.cardNumber= cardNumber; // encrypt in production
        this.cvv = cvv;               // encrypt in production
        this.expiry = expiry;
        this.lastFour = cardNumber.substring(cardNumber.length() - 4);
    }

}
