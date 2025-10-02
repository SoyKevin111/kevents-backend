package com.example.kevents.validation;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class InputValidation {

    public <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public void setIfNotBlank(String value, Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }

    public void setIfNotInteger(Integer value, Consumer<Integer> setter) {
        if (value != null && value > 0) {
            setter.accept(value);
        }
    }

}
