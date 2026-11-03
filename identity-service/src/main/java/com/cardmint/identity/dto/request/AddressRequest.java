package com.cardmint.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "Address Line 1 is required")
        @Size(max = 150, message = "Address Line 1 must be at most 150 characters")
        String line1,

        @Size(max = 150, message = "Address Line 2 must be at most 150 characters")
        String line2,

        @NotBlank(message = "City is required")
        @Size(max = 50, message = "City must be at most 50 characters")
        String city,

        @NotBlank(message = "State is required")
        @Size(max = 50, message = "State must be at most 50 characters")
        String state,

        @NotBlank(message = "Postal code is required")
        @Size(max = 15, message = "Postal code must be at most 15 characters")
        String postalCode,

        @NotBlank(message = "Country is required")
        @Size(max = 50, message = "Country must be at most 50 characters")
        String country
) {}