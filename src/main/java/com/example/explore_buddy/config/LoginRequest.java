package com.example.explore_buddy.config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginRequest {
    private final String email;
    private final String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
