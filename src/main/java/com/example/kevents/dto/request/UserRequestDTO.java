package com.example.kevents.dto.request;

import com.example.kevents.dto.validation.onCreate;
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
public class UserRequestDTO {
   @NotBlank(message = "Username is required", groups = onCreate.class)
   private String username;
   @Email(message = "Email is required", groups = onCreate.class)
   private String email;
   @NotBlank(message = "Password is required", groups = onCreate.class)
   private String password;
   @NotNull(message = "Role is required", groups = onCreate.class)
   private Role role;
}
