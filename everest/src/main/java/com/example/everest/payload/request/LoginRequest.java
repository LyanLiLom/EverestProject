package com.example.everest.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Username không được phép rỗng")
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
