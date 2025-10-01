package com.example.kevents.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse {
    private String email;
    private String accessToken;
    private String message;
}
