package com.example.kevents.dto.request;


import com.example.kevents.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
   private String username;
   private String email;
   private String password;
   private Role role;
}
