package com.example.kevents.dto.request;

import com.example.kevents.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
   @NotBlank(message = "Username is required")
   private String username;
   @Email(message = "Email is required")
   private String email;
   @NotBlank(message = "Password is required")
   private String password;
   @NotNull(message = "Role is required")
   private Role role;
}
