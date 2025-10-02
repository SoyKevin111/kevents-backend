package com.example.kevents.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.User;
import com.example.kevents.repository.UserRepository;

@Component
public class UserValidation {

    @Autowired
    UserRepository userRepository;

    public void validateEmail(User user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new ServerInternalError("Email already exists.");
        }
    }

}
