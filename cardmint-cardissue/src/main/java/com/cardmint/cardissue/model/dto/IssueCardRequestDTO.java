package com.cardmint.cardissue.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record IssueCardRequestDTO(

        Boolean isReissue,

        String userId,
        @NotBlank(message = "cardNumber is required")
        String cardNumber,
        @NotBlank(message = "cardLastFour is required")
        String cardLastFour,
        @NotBlank(message = "cardNetwork is required")
        String cardNetwork,
        @NotBlank(message = "cardProcessor is required")
        String cardProcessor,
        @NotBlank(message = "cardCvv is required")
        String cardCvv,
        @NotBlank(message = "cardExpiryDate is required")
        String cardExpiryDate,

        String fullName,

        String email,

        String phone,

        String pan,

        LocalDate dateOfBirth,

        @Valid
        AddressDTO address
) {}