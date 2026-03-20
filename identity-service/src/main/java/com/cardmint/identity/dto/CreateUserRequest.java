package com.cardmint.identity.dto;


public record CreateUserRequest(

        String fullName,
        String email,
        String phone,
        String pan
) {}
