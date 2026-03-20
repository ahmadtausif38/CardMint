package com.cardmint.identity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_users_pan_hash", columnNames = "pan_hash"),
                @UniqueConstraint(name = "ux_users_user_id", columnNames = "user_id")
        },
        indexes = {
                @Index(name = "ix_users_created_at", columnList = "created_at")
        }
)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id", length = 40, nullable = false, updatable = false)
    private String userId;

    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    // PAN is never stored raw; only store salted hash + salt
    @Column(name = "pan_hash", length = 64, nullable = false)
    private String panHash;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    private void assignUserId() {
        if (this.userId == null || this.userId.isBlank()) {
            String token = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
            this.userId = "usr-" + token;
        }
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {

        @Column(name = "address_line1", length = 150)
        private String line1;

        @Column(name = "address_line2", length = 150)
        private String line2;

        @Column(name = "city", length = 50)
        private String city;

        @Column(name = "state", length = 50)
        private String state;

        @Column(name = "postal_code", length = 15)
        private String postalCode;

        @Column(name = "country", length = 50)
        private String country;
    }
}