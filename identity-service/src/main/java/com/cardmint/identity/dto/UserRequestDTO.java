package com.cardmint.identity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserRequestDTO(
        @NotBlank(message = "Full name is required")
        @Size(max = 150, message = "Full name must be at most 150 characters")
        String fullName,

        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
        String phone,

        @NotBlank(message = "PAN is required")
        @Size(min = 10, max = 10, message = "PAN must be 10 characters")
        String pan,

        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth,

        @Valid
        @NotNull(message = "Address is required")
        AddressDTO address
) {}