package com.kraya.platform.dto;

import lombok.Data;

@Data
public class UserDeletionResponse {

    private String message;

    public UserDeletionResponse() {}

    public UserDeletionResponse(String message) {
        this.message = message;
    }
}
