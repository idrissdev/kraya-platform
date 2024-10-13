package com.kraya.platform.dto;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private Long userId;
    private String message;

    public UserRegistrationResponse() {}

    public UserRegistrationResponse(String message) {
        this.message = message;
    }

    // Getters and setters
}