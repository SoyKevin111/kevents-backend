package com.example.kevents.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.User;
import com.example.kevents.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void validateEmail(User user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new ServerInternalError("Email already exists.");
        }
    }

}
