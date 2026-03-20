package com.cardprocessor.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String accountId;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE, BLOCKED

    @Column(nullable = false)
    private Instant createdAt;

    private Instant updatedAt;

    private String currency;
}