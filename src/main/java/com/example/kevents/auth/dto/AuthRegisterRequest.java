package com.example.kevents.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthRegisterRequest {

    private String email;
    private String password;
    private String username;
}
