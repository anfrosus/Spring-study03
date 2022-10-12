package com.example.spring_hw02.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoAuthorityException extends RuntimeException{

    public NoAuthorityException(String message) {
        super(message);
    }
}
