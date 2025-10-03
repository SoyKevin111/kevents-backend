package com.example.kevents.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class UserResponse {
   private String username;
   private String email;
   private String role;
}
