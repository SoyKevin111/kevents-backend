package com.example.kevents.dto.response;

import lombok.*;

@Builder
public class UserResponse {
   private String username;
   private String email;
   private String role;
}
