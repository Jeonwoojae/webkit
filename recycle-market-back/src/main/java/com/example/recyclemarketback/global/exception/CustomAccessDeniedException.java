package com.example.recyclemarketback.global.exception;

import org.springframework.security.access.AccessDeniedException;

public class CustomAccessDeniedException extends AccessDeniedException {
    public CustomAccessDeniedException(String msg) {
        super(msg);
    }
}
