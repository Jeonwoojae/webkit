package com.example.recyclemarketback.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

@Getter
@AllArgsConstructor
public class CustomAccessDeniedException extends RuntimeException {
    private final int errorCode;
    public CustomAccessDeniedException(int errorCode,String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
